import type { ProductResponse } from '../../interfaces';

interface Props {
  product: ProductResponse;
  onClose: () => void;
  onAddToCart: (product: ProductResponse) => void;
}

export default function ProductDetail({ product, onClose, onAddToCart }: Props) {
  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40 backdrop-blur-sm p-4">
      <div className="bg-white rounded-2xl shadow-xl w-full max-w-md">
        <div className="bg-gradient-to-br from-primary-100 to-primary-50 rounded-t-2xl h-48 flex items-center justify-center">
          <span className="text-7xl select-none">📦</span>
        </div>
        <div className="p-6 flex flex-col gap-4">
          <div className="flex items-start justify-between">
            <h2 className="text-xl font-bold text-gray-900">{product.name}</h2>
            <span
              className={`text-xs px-2 py-1 rounded-full font-medium ${
                product.available
                  ? 'bg-green-100 text-green-700'
                  : 'bg-red-100 text-red-600'
              }`}
            >
              {product.available ? 'Disponible' : 'Sin stock'}
            </span>
          </div>
          <p className="text-gray-600 text-sm leading-relaxed">{product.description}</p>
          <div className="flex items-center gap-2">
            <span className="text-sm text-gray-500">ID:</span>
            <span className="text-sm font-mono text-gray-700">{product.id}</span>
          </div>
          <p className="text-2xl font-bold text-primary-700">${product.price.toFixed(2)}</p>
          <div className="flex gap-3 mt-2">
            <button
              onClick={onClose}
              className="flex-1 border border-gray-300 text-gray-700 rounded-xl py-2 hover:bg-gray-50 transition-colors"
            >
              Cerrar
            </button>
            <button
              disabled={!product.available}
              onClick={() => { onAddToCart(product); onClose(); }}
              className="flex-1 bg-primary-600 text-white rounded-xl py-2 hover:bg-primary-700 transition-colors disabled:opacity-40 disabled:cursor-not-allowed"
            >
              Agregar al carrito
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
