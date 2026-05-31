import api from './api';
import type {
  ProductResponse,
  CreateProductRequest,
  UpdateProductRequest,
  DeleteProductResponse,
} from '../interfaces';

export const productService = {
  getAll: () => api.get<ProductResponse[]>('/product/all').then((r) => r.data),
  getById: (id: number) => api.get<ProductResponse>(`/product/${id}`).then((r) => r.data),
  create: (body: CreateProductRequest) => api.post<ProductResponse>('/product', body).then((r) => r.data),
  update: (id: number, body: UpdateProductRequest) =>
    api.put<ProductResponse>(`/product/${id}`, body).then((r) => r.data),
  remove: (id: number) => api.delete<DeleteProductResponse>(`/product/${id}`).then((r) => r.data),
};
