import api from './api';
import type {
  InventoryResponse,
  CreateInventoryRequest,
  UpdateInventoryRequest,
  DeleteInventoryResponse,
  InventoryMovementResponse,
  CreateInventoryMovementRequest,
  UpdateInventoryMovementRequest,
  DeleteInventoryMovementResponse,
} from '../interfaces';

export const inventoryService = {
  getAll: () => api.get<InventoryResponse[]>('/inventory/all').then((r) => r.data),
  getById: (id: number) => api.get<InventoryResponse>(`/inventory/${id}`).then((r) => r.data),
  getByProductId: (productId: number) =>
    api.get<InventoryResponse>(`/inventory/product/${productId}`).then((r) => r.data),
  create: (body: CreateInventoryRequest) =>
    api.post<InventoryResponse>('/inventory', body).then((r) => r.data),
  update: (id: number, body: UpdateInventoryRequest) =>
    api.put<InventoryResponse>(`/inventory/${id}`, body).then((r) => r.data),
  remove: (id: number) =>
    api.delete<DeleteInventoryResponse>(`/inventory/${id}`).then((r) => r.data),
};

export const inventoryMovementService = {
  getAll: () =>
    api.get<InventoryMovementResponse[]>('/inventory-movement/all').then((r) => r.data),
  getById: (id: number) =>
    api.get<InventoryMovementResponse>(`/inventory-movement/${id}`).then((r) => r.data),
  create: (body: CreateInventoryMovementRequest) =>
    api.post<InventoryMovementResponse>('/inventory-movement', body).then((r) => r.data),
  update: (id: number, body: UpdateInventoryMovementRequest) =>
    api.put<InventoryMovementResponse>(`/inventory-movement/${id}`, body).then((r) => r.data),
  remove: (id: number) =>
    api.delete<DeleteInventoryMovementResponse>(`/inventory-movement/${id}`).then((r) => r.data),
};
