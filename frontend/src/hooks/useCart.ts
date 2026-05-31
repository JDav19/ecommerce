import { useState, useCallback } from 'react';
import { cartService } from '../services/cartService';
import { cartItemService } from '../services/cartItemService';
import type { CartResponse, CartItemResponse } from '../interfaces';

export function useCart() {
  const [cart, setCart] = useState<CartResponse | null>(null);
  const [items, setItems] = useState<CartItemResponse[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const loadCart = useCallback(async (cartId: number) => {
    setLoading(true);
    setError(null);
    try {
      const [cartData, itemsData] = await Promise.all([
        cartService.getById(cartId),
        cartItemService.getByCart(cartId),
      ]);
      setCart(cartData);
      setItems(itemsData);
    } catch (e) {
      setError((e as Error).message);
    } finally {
      setLoading(false);
    }
  }, []);

  const addItem = useCallback(async (cartId: number, productId: number, quantity: number) => {
    const newItem = await cartItemService.add({ cartId, productId, quantity });
    setItems((prev) => {
      const existing = prev.find((i) => i.id === newItem.id);
      return existing ? prev.map((i) => (i.id === newItem.id ? newItem : i)) : [...prev, newItem];
    });
  }, []);

  const updateQuantity = useCallback(async (itemId: number, quantity: number) => {
    const updated = await cartItemService.updateQuantity(itemId, { quantity });
    setItems((prev) => prev.map((i) => (i.id === itemId ? updated : i)));
  }, []);

  const removeItem = useCallback(async (itemId: number) => {
    await cartItemService.remove(itemId);
    setItems((prev) => prev.filter((i) => i.id !== itemId));
  }, []);

  const total = items.reduce((sum, i) => sum + i.quantity, 0);

  return { cart, items, loading, error, total, loadCart, addItem, updateQuantity, removeItem };
}
