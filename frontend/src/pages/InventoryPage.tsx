import { useState } from 'react';
import InventoryList from '../components/inventory/InventoryList';
import InventoryMovements from '../components/inventory/InventoryMovements';

export default function InventoryPage() {
  const [tab, setTab] = useState<'stock' | 'movements'>('stock');

  return (
    <div>
      <h1 className="text-2xl font-bold text-gray-900 mb-6">Gestión de Inventario</h1>
      <div className="flex gap-2 mb-6 border-b border-gray-200">
        {(['stock', 'movements'] as const).map((t) => (
          <button
            key={t}
            onClick={() => setTab(t)}
            className={`pb-3 px-4 text-sm font-medium capitalize transition-colors ${
              tab === t
                ? 'border-b-2 border-primary-600 text-primary-600'
                : 'text-gray-500 hover:text-gray-700'
            }`}
          >
            {t === 'stock' ? 'Stock actual' : 'Movimientos'}
          </button>
        ))}
      </div>
      {tab === 'stock' ? <InventoryList /> : <InventoryMovements />}
    </div>
  );
}
