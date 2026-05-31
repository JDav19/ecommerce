import type { CartItemResponse } from '../../interfaces';

interface Props {
  item: CartItemResponse;
  onUpdate: (id: number, qty: number) => void;
  onRemove: (id: number) => void;
}

export default function CartItemRow({ item, onUpdate, onRemove }: Props) {
  return (
    <div className="flex items-center gap-4 py-4 border-b border-gray-100 last:border-0">
      <div className="w-12 h-12 bg-primary-50 rounded-lg flex items-center justify-center text-2xl select-none flex-shrink-0">
        📦
      </div>
      <div className="flex-1 min-w-0">
        <p className="font-medium text-gray-900 truncate">{item.productName}</p>
        <p className="text-xs text-gray-400">ID producto: {item.productId}</p>
      </div>
      <div className="flex items-center gap-2">
        <button
          onClick={() => item.quantity > 1 && onUpdate(item.id, item.quantity - 1)}
          className="w-7 h-7 rounded-full border border-gray-300 text-gray-600 hover:bg-gray-100 flex items-center justify-center text-sm disabled:opacity-30"
          disabled={item.quantity <= 1}
        >
          −
        </button>
        <span className="w-8 text-center text-sm font-semibold">{item.quantity}</span>
        <button
          onClick={() => onUpdate(item.id, item.quantity + 1)}
          className="w-7 h-7 rounded-full border border-gray-300 text-gray-600 hover:bg-gray-100 flex items-center justify-center text-sm"
        >
          +
        </button>
      </div>
      <button
        onClick={() => onRemove(item.id)}
        className="text-red-400 hover:text-red-600 transition-colors ml-2 text-sm"
        title="Eliminar"
      >
        ✕
      </button>
    </div>
  );
}
