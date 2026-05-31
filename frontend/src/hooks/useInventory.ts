import { useState, useEffect, useCallback } from 'react';
import { inventoryService, inventoryMovementService } from '../services/inventoryService';
import type { InventoryResponse, InventoryMovementResponse } from '../interfaces';

export function useInventory() {
  const [inventory, setInventory] = useState<InventoryResponse[]>([]);
  const [movements, setMovements] = useState<InventoryMovementResponse[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const fetchAll = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const [inv, mov] = await Promise.all([
        inventoryService.getAll(),
        inventoryMovementService.getAll(),
      ]);
      setInventory(inv);
      setMovements(mov);
    } catch (e) {
      setError((e as Error).message);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => { fetchAll(); }, [fetchAll]);

  const updateStock = useCallback(async (id: number, stock: number) => {
    const updated = await inventoryService.update(id, { stock });
    setInventory((prev) => prev.map((i) => (i.id === id ? updated : i)));
  }, []);

  return { inventory, movements, loading, error, refetch: fetchAll, updateStock };
}
