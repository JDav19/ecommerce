import api from './api';
import type {
  CategoryResponse,
  CreateCategoryRequest,
  UpdateCategoryRequest,
  DeleteCategoryResponse,
  ProductCategoryResponse,
  CreateProductCategoryRequest,
} from '../interfaces';

export const categoryService = {
  getAll: () => api.get<CategoryResponse[]>('/category/all').then((r) => r.data),
  getById: (id: number) => api.get<CategoryResponse>(`/category/${id}`).then((r) => r.data),
  create: (body: CreateCategoryRequest) =>
    api.post<CategoryResponse>('/category', body).then((r) => r.data),
  update: (id: number, body: UpdateCategoryRequest) =>
    api.put<CategoryResponse>(`/category/${id}`, body).then((r) => r.data),
  remove: (id: number) =>
    api.delete<DeleteCategoryResponse>(`/category/${id}`).then((r) => r.data),
};

export const productCategoryService = {
  getAll: () =>
    api.get<ProductCategoryResponse[]>('/product-category/all').then((r) => r.data),
  create: (body: CreateProductCategoryRequest) =>
    api.post<ProductCategoryResponse>('/product-category', body).then((r) => r.data),
};
