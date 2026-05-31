import api from './api';
import type {
  UserResponse,
  CreateUserRequest,
  UpdateUserRequest,
  DeleteUserResponse,
  DocumentTypeResponse,
  CreateDocumentTypeRequest,
} from '../interfaces';

export const userService = {
  getAll: () => api.get<UserResponse[]>('/user/all').then((r) => r.data),
  getById: (id: number) => api.get<UserResponse>(`/user/${id}`).then((r) => r.data),
  getByEmail: (email: string) =>
    api.get<UserResponse>(`/user/email/${email}`).then((r) => r.data),
  create: (body: CreateUserRequest) =>
    api.post<UserResponse>('/user', body).then((r) => r.data),
  update: (id: number, body: UpdateUserRequest) =>
    api.put<UserResponse>(`/user/${id}`, body).then((r) => r.data),
  remove: (id: number) =>
    api.delete<DeleteUserResponse>(`/user/${id}`).then((r) => r.data),
};

export const documentTypeService = {
  getAll: () => api.get<DocumentTypeResponse[]>('/document-type/all').then((r) => r.data),
  create: (body: CreateDocumentTypeRequest) =>
    api.post<DocumentTypeResponse>('/document-type', body).then((r) => r.data),
};
