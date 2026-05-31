import { useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import type { AxiosError } from 'axios';
import { cartItemService } from '../../services/cartItemService';
import { cartService } from '../../services/cartService';
import { productService } from '../../services/productService';
import { userService } from '../../services/userService';
import { orderService, orderItemService } from '../../services/orderService';
import { paymentService } from '../../services/paymentService';
import type { UserResponse, CartItemResponse } from '../../interfaces';

// Moneda fija para todo el sistema
const CURRENCY = 'COP';

// CartItemResponse enriquecido con precio real del producto
interface EnrichedItem extends CartItemResponse {
  unitPrice: number;
  lineTotal: number;
}

// ─── Helpers ────────────────────────────────────────────────────────────────

function logStep(step: string, e: unknown) {
  const axiosErr = e as AxiosError;
  console.error(`[Checkout] Error en "${step}":`, axiosErr.response?.data ?? axiosErr);
}

// ─── Componente ─────────────────────────────────────────────────────────────

export default function Checkout() {
  const navigate = useNavigate();

  // Lista de usuarios para el selector
  const [users, setUsers] = useState<UserResponse[]>([]);
  const [selectedUserId, setSelectedUserId] = useState<number | ''>('');

  // Carrito activo del usuario seleccionado
  const [activeCartId, setActiveCartId] = useState<number | null>(null);

  // Ítems enriquecidos con precio real
  const [items, setItems] = useState<EnrichedItem[]>([]);

  // Estados de carga independientes
  const [loadingUsers, setLoadingUsers] = useState(true);
  const [loadingCart, setLoadingCart] = useState(false);
  const [submitting, setSubmitting] = useState(false);

  // Mensajes de UI
  const [error, setError] = useState<string | null>(null);
  const [cartWarning, setCartWarning] = useState<string | null>(null);
  const [success, setSuccess] = useState(false);

  // Campo opcional del formulario
  const [providerRef, setProviderRef] = useState('');

  // ── 1. Cargar lista de usuarios al montar ──────────────────────────────────
  useEffect(() => {
    userService
      .getAll()
      .then(setUsers)
      .catch((e) => {
        logStep('cargar usuarios', e);
        setError('No se pudo cargar la lista de usuarios.');
      })
      .finally(() => setLoadingUsers(false));
  }, []);

  // ── 2. Cuando cambia el usuario → buscar carrito activo → cargar ítems ─────
  const loadCartForUser = useCallback(async (userId: number) => {
    setLoadingCart(true);
    setCartWarning(null);
    setError(null);
    setItems([]);
    setActiveCartId(null);

    try {
      // Buscar carrito ACTIVO del usuario en la lista global
      const allCarts = await cartService.getAll();
      const activeCart = allCarts.find(
        (c) => c.userId === userId && c.status === 'ACTIVE'
      );

      if (!activeCart) {
        setCartWarning('Este usuario no tiene un carrito activo. Agrega productos desde el catálogo primero.');
        return;
      }

      setActiveCartId(activeCart.id);

      // Obtener ítems del carrito
      const cartItems = await cartItemService.getByCart(activeCart.id);

      if (cartItems.length === 0) {
        setCartWarning('El carrito activo está vacío.');
        return;
      }

      // Por cada ítem, consultar precio real con productService.getById
      const enriched: EnrichedItem[] = await Promise.all(
        cartItems.map(async (item) => {
          const product = await productService.getById(item.productId);
          return {
            ...item,
            unitPrice: product.price,
            lineTotal: item.quantity * product.price,
          };
        })
      );

      setItems(enriched);
    } catch (e) {
      logStep('cargar carrito del usuario', e);
      setError((e as Error).message);
    } finally {
      setLoadingCart(false);
    }
  }, []);

  useEffect(() => {
    if (selectedUserId !== '') {
      loadCartForUser(selectedUserId as number);
    }
  }, [selectedUserId, loadCartForUser]);

  // Total monetario real calculado de los ítems enriquecidos
  const totalAmount = items.reduce((sum, i) => sum + i.lineTotal, 0);

  // ── 3. Envío del pedido ────────────────────────────────────────────────────
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!selectedUserId || !activeCartId || items.length === 0) return;

    setSubmitting(true);
    setError(null);

    let orderId: number | null = null;

    // Paso A — Crear la orden
    try {
      const order = await orderService.create({
        userId: selectedUserId as number,
        totalAmount,
        currency: CURRENCY,
      });
      orderId = order.id;
    } catch (e) {
      logStep('crear orden', e);
      setError(`Error al crear la orden: ${(e as Error).message}`);
      setSubmitting(false);
      return;
    }

    // Paso B — Crear los ítems de la orden
    try {
      await Promise.all(
        items.map((item) =>
          orderItemService.create({
            orderId: orderId!,
            productId: item.productId,
            quantity: item.quantity,
            unitPriceSnapshot: item.unitPrice,
          })
        )
      );
    } catch (e) {
      logStep('guardar ítems del pedido', e);
      setError(`Error al guardar los ítems del pedido: ${(e as Error).message}`);
      setSubmitting(false);
      return;
    }

    // Paso C — Registrar el pago
    try {
      await paymentService.create({
        orderId: orderId!,
        providerRef: providerRef.trim() || 'MANUAL',
        idempotencyKey: `order-${orderId}-${Date.now()}`,
      });
    } catch (e) {
      logStep('registrar pago', e);
      setError(`Error al registrar el pago: ${(e as Error).message}`);
      setSubmitting(false);
      return;
    }

    // Paso D — Cerrar el carrito (no bloquea el éxito si falla)
    try {
      await cartService.update(activeCartId, { status: 'CLOSED' });
    } catch (e) {
      logStep('cerrar carrito', e);
    }

    // Paso E — Eliminar los cart-items del backend uno a uno
    try {
      await Promise.all(items.map((item) => cartItemService.remove(item.id)));
    } catch (e) {
      logStep('eliminar ítems del carrito', e);
      // No bloqueante: los ítems pertenecen al carrito ya cerrado
    }

    // Limpieza del estado local
    setItems([]);
    setActiveCartId(null);

    // Notificar a toda la app que el carrito quedó en 0
    window.dispatchEvent(new CustomEvent('cart-updated', { detail: { count: 0 } }));

    setSubmitting(false);
    setSuccess(true);
  };

  // ── Pantalla de éxito ──────────────────────────────────────────────────────
  if (success) {
    return (
      <div className="max-w-md mx-auto text-center py-20 flex flex-col items-center gap-4">
        <p className="text-7xl select-none">✅</p>
        <h2 className="text-2xl font-bold text-gray-900">¡Pedido confirmado!</h2>
        <p className="text-gray-500 text-sm">
          Tu pedido fue creado, los ítems registrados y el pago procesado correctamente.
        </p>
        <button
          type="button"
          onClick={() => navigate('/orders')}
          className="mt-4 bg-primary-600 text-white px-6 py-2.5 rounded-xl hover:bg-primary-700 transition-colors"
        >
          Ver mis pedidos
        </button>
        <button
          type="button"
          onClick={() => navigate('/')}
          className="text-primary-600 underline text-sm"
        >
          Seguir comprando
        </button>
      </div>
    );
  }

  // ── Pantalla principal ─────────────────────────────────────────────────────
  return (
    <div className="max-w-2xl mx-auto">
      <h1 className="text-2xl font-bold text-gray-900 mb-6">Checkout</h1>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

        {/* ── Resumen del carrito ───────────────────────────────────── */}
        <div>
          <h2 className="text-base font-semibold text-gray-700 mb-3">Resumen del carrito</h2>
          <div className="bg-white rounded-xl border border-gray-100 shadow-sm divide-y divide-gray-100 min-h-[120px]">

            {loadingCart && (
              <div className="flex items-center justify-center py-8 gap-2 text-sm text-gray-400">
                <div className="w-5 h-5 border-4 border-primary-500 border-t-transparent rounded-full animate-spin" />
                <span>Cargando carrito…</span>
              </div>
            )}

            {!loadingCart && cartWarning && (
              <div className="px-4 py-5 text-center text-amber-600 text-sm">
                {cartWarning}
              </div>
            )}

            {!loadingCart && !cartWarning && items.length === 0 && selectedUserId === '' && (
              <p className="px-4 py-6 text-center text-gray-400 text-sm">
                Selecciona un usuario para ver su carrito.
              </p>
            )}

            {items.map((item) => (
              <div key={item.id} className="px-4 py-3 text-sm">
                <div className="flex justify-between">
                  <span className="text-gray-800 font-medium">{item.productName}</span>
                  <span className="text-gray-900 font-semibold">
                    ${item.lineTotal.toLocaleString('es-CO', { minimumFractionDigits: 2 })}
                  </span>
                </div>
                <div className="flex justify-between text-gray-400 text-xs mt-0.5">
                  <span>x{item.quantity} unidades</span>
                  <span>
                    ${item.unitPrice.toLocaleString('es-CO', { minimumFractionDigits: 2 })} c/u
                  </span>
                </div>
              </div>
            ))}

            {items.length > 0 && (
              <div className="px-4 py-3 flex justify-between font-bold text-sm bg-gray-50 rounded-b-xl">
                <span>Total ({CURRENCY})</span>
                <span className="text-primary-700 text-base">
                  ${totalAmount.toLocaleString('es-CO', { minimumFractionDigits: 2 })}
                </span>
              </div>
            )}
          </div>
        </div>

        {/* ── Formulario del pedido ─────────────────────────────────── */}
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <h2 className="text-base font-semibold text-gray-700">Datos del pedido</h2>

          {error && (
            <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-xl text-sm">
              {error}
            </div>
          )}

          {/* Selector de usuario */}
          <div>
            <label
              htmlFor="userSelect"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              Usuario
            </label>
            {loadingUsers ? (
              <div className="w-full border border-gray-200 bg-gray-50 rounded-xl px-4 py-2 text-sm text-gray-400">
                Cargando usuarios…
              </div>
            ) : (
              <select
                id="userSelect"
                title="Usuario"
                value={selectedUserId}
                onChange={(e) =>
                  setSelectedUserId(e.target.value === '' ? '' : Number(e.target.value))
                }
                required
                className="w-full border border-gray-300 rounded-xl px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-primary-500"
              >
                <option value="">— Selecciona un usuario —</option>
                {users.map((u) => (
                  <option key={u.id} value={u.id}>
                    #{u.id} · {u.fullName} ({u.email})
                  </option>
                ))}
              </select>
            )}
          </div>

          {/* Moneda fija — solo informativa */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Moneda</label>
            <div className="w-full border border-gray-200 bg-gray-50 rounded-xl px-4 py-2 text-sm text-gray-500 flex items-center justify-between">
              <span>Peso Colombiano</span>
              <span className="text-xs bg-green-100 text-green-700 px-2 py-0.5 rounded-full font-medium">
                {CURRENCY}
              </span>
            </div>
          </div>

          {/* Referencia de pago opcional */}
          <div>
            <label
              htmlFor="providerRef"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              Referencia de pago{' '}
              <span className="text-gray-400 font-normal">(opcional)</span>
            </label>
            <input
              id="providerRef"
              type="text"
              value={providerRef}
              onChange={(e) => setProviderRef(e.target.value)}
              placeholder="Ej: STRIPE_123"
              className="w-full border border-gray-300 rounded-xl px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-primary-500"
            />
          </div>

          <button
            type="submit"
            disabled={submitting || loadingCart || items.length === 0 || !selectedUserId}
            className="w-full bg-primary-600 text-white rounded-xl py-3 font-semibold hover:bg-primary-700 transition-colors disabled:opacity-40 disabled:cursor-not-allowed mt-2"
          >
            {submitting
              ? 'Procesando…'
              : items.length > 0
              ? `Confirmar pedido · $${totalAmount.toLocaleString('es-CO', { minimumFractionDigits: 2 })} ${CURRENCY}`
              : 'Confirmar pedido'}
          </button>
        </form>
      </div>
    </div>
  );
}
