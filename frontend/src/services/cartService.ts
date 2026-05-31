import api from './api';
import type {
  CartResponse,
  CreateCartRequest,
  UpdateCartRequest,
  DeleteCartResponse,
} from '../interfaces';

export const cartService = {
  getAll: () => api.get<CartResponse[]>('/cart/all').then((r) => r.data),
  getById: (id: number) => api.get<CartResponse>(`/cart/${id}`).then((r) => r.data),
  create: (body: CreateCartRequest) => api.post<CartResponse>('/cart', body).then((r) => r.data),
  update: (id: number, body: UpdateCartRequest) =>
    api.put<CartResponse>(`/cart/${id}`, body).then((r) => r.data),
  remove: (id: number) => api.delete<DeleteCartResponse>(`/cart/${id}`).then((r) => r.data),
};
