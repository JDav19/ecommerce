import { useState } from 'react';
import { useProducts } from '../../hooks/useProducts';
import { cartItemService } from '../../services/cartItemService';
import ProductCard from './ProductCard';
import ProductDetail from './ProductDetail';
import type { ProductResponse } from '../../interfaces';

const FIXED_CART_ID = 1;

export default function ProductCatalog() {
  const { products, loading, error, refetch } = useProducts();
  const [selected, setSelected] = useState<ProductResponse | null>(null);
  const [search, setSearch] = useState('');
  const [toast, setToast] = useState<string | null>(null);

  const showToast = (msg: string) => {
    setToast(msg);
    setTimeout(() => setToast(null), 3000);
  };

  const handleAddToCart = async (product: ProductResponse) => {
    try {
      await cartItemService.add({ cartId: FIXED_CART_ID, productId: product.id, quantity: 1 });
      showToast(`"${product.name}" agregado al carrito`);
    } catch (e) {
      console.error('Detalle del error en la API:', e);
      showToast((e as Error).message);
    }
  };

  const filtered = products.filter((p) =>
    p.name.toLowerCase().includes(search.toLowerCase()) ||
    p.description?.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div>
      {toast && (
        <div className="fixed top-4 right-4 z-50 bg-gray-900 text-white px-4 py-3 rounded-xl shadow-lg text-sm animate-pulse">
          {toast}
        </div>
      )}

      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-6">
        <h1 className="text-2xl font-bold text-gray-900">Catálogo de Productos</h1>
        <div className="flex gap-3">
          <input
            type="text"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            placeholder="Buscar productos..."
            className="border border-gray-300 rounded-xl px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-primary-500 w-60"
          />
          <button
            onClick={refetch}
            className="px-4 py-2 border border-gray-300 rounded-xl text-sm text-gray-600 hover:bg-gray-100 transition-colors"
          >
            Recargar
          </button>
        </div>
      </div>

      {loading && (
        <div className="flex justify-center py-16">
          <div className="w-10 h-10 border-4 border-primary-500 border-t-transparent rounded-full animate-spin" />
        </div>
      )}

      {error && (
        <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-xl mb-4 text-sm">
          Error: {error}
        </div>
      )}

      {!loading && !error && filtered.length === 0 && (
        <p className="text-center text-gray-400 py-16">No se encontraron productos.</p>
      )}

      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        {filtered.map((p) => (
          <ProductCard
            key={p.id}
            product={p}
            onViewDetail={setSelected}
            onAddToCart={handleAddToCart}
          />
        ))}
      </div>

      {selected && (
        <ProductDetail
          product={selected}
          onClose={() => setSelected(null)}
          onAddToCart={handleAddToCart}
        />
      )}
    </div>
  );
}
