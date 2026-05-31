import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useCart } from '../../hooks/useCart';
import CartItemRow from './CartItemRow';

const FIXED_CART_ID = 1;

export default function Cart() {
  const navigate = useNavigate();
  const { cart, items, loading, error, loadCart, updateQuantity, removeItem } = useCart();

  useEffect(() => {
    loadCart(FIXED_CART_ID);
  }, [loadCart]);

  useEffect(() => {
    if (error) {
      console.error('Detalle del error en la API:', error);
    }
  }, [error]);

  // Sincroniza el contador del navbar cada vez que cambia la lista de ítems
  useEffect(() => {
    const total = items.reduce((s, i) => s + i.quantity, 0);
    window.dispatchEvent(new CustomEvent('cart-updated', { detail: { count: total } }));
  }, [items]);

  const totalItems = items.reduce((s, i) => s + i.quantity, 0);

  return (
    <div className="max-w-2xl mx-auto">
      <h1 className="text-2xl font-bold text-gray-900 mb-6">Carrito de Compras</h1>

      {loading && (
        <div className="flex justify-center py-10">
          <div className="w-8 h-8 border-4 border-primary-500 border-t-transparent rounded-full animate-spin" />
        </div>
      )}

      {error && (
        <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-xl mb-4 text-sm">
          {error}
        </div>
      )}

      {!loading && items.length === 0 && (
        <div className="flex flex-col items-center py-16 gap-3">
          <p className="text-5xl select-none">🛒</p>
          <p className="text-gray-400">El carrito está vacío</p>
          <button
            onClick={() => navigate('/')}
            className="text-primary-600 underline text-sm"
          >
            Agregar productos
          </button>
        </div>
      )}

      {items.length > 0 && (
        <>
          {cart && (
            <div className="bg-white rounded-xl shadow-sm border border-gray-100 p-4 mb-4 flex gap-6 text-sm text-gray-500">
              <span>Carrito #{cart.id}</span>
              <span className="font-medium text-gray-700">{cart.userEmail}</span>
              <span
                className={`px-2 py-0.5 rounded-full text-xs font-medium ${
                  cart.status === 'ACTIVE' ? 'bg-green-100 text-green-700' : 'bg-gray-100'
                }`}
              >
                {cart.status}
              </span>
            </div>
          )}

          <div className="bg-white rounded-xl shadow-sm border border-gray-100 px-4">
            {items.map((item) => (
              <CartItemRow
                key={item.id}
                item={item}
                onUpdate={updateQuantity}
                onRemove={removeItem}
              />
            ))}
          </div>

          <div className="bg-white rounded-xl shadow-sm border border-gray-100 p-4 mt-4 flex items-center justify-between">
            <div>
              <p className="text-sm text-gray-500">Total de ítems</p>
              <p className="text-lg font-bold text-gray-900">{totalItems}</p>
            </div>
            <button
              onClick={() => navigate('/checkout')}
              className="bg-primary-600 text-white px-6 py-2.5 rounded-xl hover:bg-primary-700 transition-colors font-medium"
            >
              Proceder al pago
            </button>
          </div>
        </>
      )}
    </div>
  );
}
