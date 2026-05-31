import { useInventory } from '../../hooks/useInventory';

export default function InventoryMovements() {
  const { movements, loading } = useInventory();

  const badge = (type: string) => {
    const map: Record<string, string> = {
      IN: 'bg-green-100 text-green-700',
      OUT: 'bg-red-100 text-red-600',
      ADJUSTMENT: 'bg-yellow-100 text-yellow-700',
    };
    return map[type] ?? 'bg-gray-100 text-gray-600';
  };

  return (
    <div>
      <h2 className="text-xl font-bold text-gray-900 mb-6">Movimientos de Inventario</h2>

      {loading && (
        <div className="flex justify-center py-10">
          <div className="w-8 h-8 border-4 border-primary-500 border-t-transparent rounded-full animate-spin" />
        </div>
      )}

      <div className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden">
        <table className="w-full text-sm">
          <thead className="bg-gray-50 text-gray-500 uppercase text-xs">
            <tr>
              <th className="px-4 py-3 text-left">ID</th>
              <th className="px-4 py-3 text-left">Producto</th>
              <th className="px-4 py-3 text-left">Orden</th>
              <th className="px-4 py-3 text-left">Tipo</th>
              <th className="px-4 py-3 text-left">Cantidad</th>
              <th className="px-4 py-3 text-left">Fecha</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-100">
            {movements.map((m) => (
              <tr key={m.id} className="hover:bg-gray-50 transition-colors">
                <td className="px-4 py-3 text-gray-400 font-mono">{m.id}</td>
                <td className="px-4 py-3 font-medium text-gray-900">{m.productName}</td>
                <td className="px-4 py-3 text-gray-500">{m.orderId ?? '—'}</td>
                <td className="px-4 py-3">
                  <span className={`px-2 py-0.5 rounded-full text-xs font-semibold ${badge(m.type)}`}>
                    {m.type}
                  </span>
                </td>
                <td className="px-4 py-3 font-semibold">{m.qty}</td>
                <td className="px-4 py-3 text-gray-400 text-xs">
                  {new Date(m.createdAt).toLocaleString('es-ES')}
                </td>
              </tr>
            ))}
            {!loading && movements.length === 0 && (
              <tr>
                <td colSpan={6} className="px-4 py-8 text-center text-gray-400">
                  No hay movimientos registrados.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
