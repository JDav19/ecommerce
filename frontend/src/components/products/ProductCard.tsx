import type { ProductResponse } from '../../interfaces';

interface Props {
  product: ProductResponse;
  onViewDetail: (product: ProductResponse) => void;
  onAddToCart: (product: ProductResponse) => void;
}

export default function ProductCard({ product, onViewDetail, onAddToCart }: Props) {
  return (
    <div className="bg-white rounded-xl shadow-sm border border-gray-100 flex flex-col overflow-hidden hover:shadow-md transition-shadow">
      <div className="bg-gradient-to-br from-primary-100 to-primary-50 h-40 flex items-center justify-center">
        <span className="text-5xl select-none">📦</span>
      </div>
      <div className="p-4 flex flex-col flex-1 gap-2">
        <h3 className="font-semibold text-gray-900 line-clamp-1">{product.name}</h3>
        <p className="text-sm text-gray-500 line-clamp-2 flex-1">{product.description}</p>
        <div className="flex items-center justify-between mt-2">
          <span className="text-lg font-bold text-primary-700">
            ${product.price.toFixed(2)}
          </span>
          <span
            className={`text-xs px-2 py-0.5 rounded-full font-medium ${
              product.available
                ? 'bg-green-100 text-green-700'
                : 'bg-red-100 text-red-600'
            }`}
          >
            {product.available ? 'Disponible' : 'Sin stock'}
          </span>
        </div>
        <div className="flex gap-2 mt-2">
          <button
            onClick={() => onViewDetail(product)}
            className="flex-1 text-sm border border-primary-600 text-primary-600 rounded-lg py-1.5 hover:bg-primary-50 transition-colors"
          >
            Ver detalle
          </button>
          <button
            disabled={!product.available}
            onClick={() => onAddToCart(product)}
            className="flex-1 text-sm bg-primary-600 text-white rounded-lg py-1.5 hover:bg-primary-700 transition-colors disabled:opacity-40 disabled:cursor-not-allowed"
          >
            Agregar
          </button>
        </div>
      </div>
    </div>
  );
}
