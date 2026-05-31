import api from './api';
import type {
  OrderResponse,
  CreateOrderRequest,
  UpdateOrderRequest,
  DeleteOrderResponse,
  OrderItemResponse,
  CreateOrderItemRequest,
  UpdateOrderItemRequest,
  DeleteOrderItemResponse,
} from '../interfaces';

export const orderService = {
  getAll: () => api.get<OrderResponse[]>('/order/all').then((r) => r.data),
  getById: (id: number) => api.get<OrderResponse>(`/order/${id}`).then((r) => r.data),
  create: (body: CreateOrderRequest) =>
    api.post<OrderResponse>('/order', body).then((r) => r.data),
  update: (id: number, body: UpdateOrderRequest) =>
    api.put<OrderResponse>(`/order/${id}`, body).then((r) => r.data),
  remove: (id: number) =>
    api.delete<DeleteOrderResponse>(`/order/${id}`).then((r) => r.data),
};

export const orderItemService = {
  getAll: () => api.get<OrderItemResponse[]>('/order-item/all').then((r) => r.data),
  getById: (id: number) => api.get<OrderItemResponse>(`/order-item/${id}`).then((r) => r.data),
  create: (body: CreateOrderItemRequest) =>
    api.post<OrderItemResponse>('/order-item', body).then((r) => r.data),
  update: (id: number, body: UpdateOrderItemRequest) =>
    api.put<OrderItemResponse>(`/order-item/${id}`, body).then((r) => r.data),
  remove: (id: number) =>
    api.delete<DeleteOrderItemResponse>(`/order-item/${id}`).then((r) => r.data),
};
