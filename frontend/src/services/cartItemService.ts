import api from './api';
import type {
  CartItemResponse,
  CreateCartItemRequest,
  UpdateCartItemRequest,
  DeleteCartItemResponse,
} from '../interfaces';

export const cartItemService = {
  getAll: () => api.get<CartItemResponse[]>('/cart-item/all').then((r) => r.data),
  getByCart: (cartId: number) =>
    api.get<CartItemResponse[]>(`/cart-item/cart/${cartId}`).then((r) => r.data),
  add: (body: CreateCartItemRequest) =>
    api.post<CartItemResponse>('/cart-item', body).then((r) => r.data),
  updateQuantity: (id: number, body: UpdateCartItemRequest) =>
    api.put<CartItemResponse>(`/cart-item/${id}`, body).then((r) => r.data),
  remove: (id: number) =>
    api.delete<DeleteCartItemResponse>(`/cart-item/${id}`).then((r) => r.data),
};
