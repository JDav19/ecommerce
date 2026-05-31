import { useState } from 'react';
import { useInventory } from '../../hooks/useInventory';

export default function InventoryList() {
  const { inventory, loading, error, refetch, updateStock } = useInventory();
  const [editId, setEditId] = useState<number | null>(null);
  const [editValue, setEditValue] = useState('');

  const startEdit = (id: number, current: number) => {
    setEditId(id);
    setEditValue(String(current));
  };

  const saveEdit = async (id: number) => {
    await updateStock(id, Number(editValue));
    setEditId(null);
  };

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <h2 className="text-xl font-bold text-gray-900">Stock de Inventario</h2>
        <button
          onClick={refetch}
          className="px-4 py-2 border border-gray-300 rounded-xl text-sm text-gray-600 hover:bg-gray-100 transition-colors"
        >
          Recargar
        </button>
      </div>

      {loading && (
        <div className="flex justify-center py-10">
          <div className="w-8 h-8 border-4 border-primary-500 border-t-transparent rounded-full animate-spin" />
        </div>
      )}

      {error && (
        <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-xl text-sm mb-4">
          {error}
        </div>
      )}

      <div className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden">
        <table className="w-full text-sm">
          <thead className="bg-gray-50 text-gray-500 uppercase text-xs">
            <tr>
              <th className="px-4 py-3 text-left">ID</th>
              <th className="px-4 py-3 text-left">Producto</th>
              <th className="px-4 py-3 text-left">ID Prod.</th>
              <th className="px-4 py-3 text-left">Stock</th>
              <th className="px-4 py-3 text-left">Acciones</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-100">
            {inventory.map((item) => (
              <tr key={item.id} className="hover:bg-gray-50 transition-colors">
                <td className="px-4 py-3 text-gray-400 font-mono">{item.id}</td>
                <td className="px-4 py-3 font-medium text-gray-900">{item.productName}</td>
                <td className="px-4 py-3 text-gray-500">{item.productId}</td>
                <td className="px-4 py-3">
                  {editId === item.id ? (
                    <input
                      type="number"
                      value={editValue}
                      onChange={(e) => setEditValue(e.target.value)}
                      className="w-20 border border-gray-300 rounded-lg px-2 py-1 text-sm focus:outline-none focus:ring-2 focus:ring-primary-500"
                      min={0}
                    />
                  ) : (
                    <span
                      className={`px-2 py-1 rounded-full text-xs font-semibold ${
                        item.stock > 10
                          ? 'bg-green-100 text-green-700'
                          : item.stock > 0
                          ? 'bg-yellow-100 text-yellow-700'
                          : 'bg-red-100 text-red-600'
                      }`}
                    >
                      {item.stock}
                    </span>
                  )}
                </td>
                <td className="px-4 py-3">
                  {editId === item.id ? (
                    <div className="flex gap-2">
                      <button
                        onClick={() => saveEdit(item.id)}
                        className="text-green-600 hover:text-green-800 text-xs font-medium"
                      >
                        Guardar
                      </button>
                      <button
                        onClick={() => setEditId(null)}
                        className="text-gray-400 hover:text-gray-600 text-xs"
                      >
                        Cancelar
                      </button>
                    </div>
                  ) : (
                    <button
                      onClick={() => startEdit(item.id, item.stock)}
                      className="text-primary-600 hover:text-primary-800 text-xs font-medium"
                    >
                      Editar stock
                    </button>
                  )}
                </td>
              </tr>
            ))}
            {!loading && inventory.length === 0 && (
              <tr>
                <td colSpan={5} className="px-4 py-8 text-center text-gray-400">
                  No hay registros de inventario.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
