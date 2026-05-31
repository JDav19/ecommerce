import api from './api';
import type {
  PaymentResponse,
  CreatePaymentRequest,
  UpdatePaymentRequest,
  DeletePaymentResponse,
} from '../interfaces';

export const paymentService = {
  getAll: () => api.get<PaymentResponse[]>('/payment/all').then((r) => r.data),
  getById: (id: number) => api.get<PaymentResponse>(`/payment/${id}`).then((r) => r.data),
  create: (body: CreatePaymentRequest) =>
    api.post<PaymentResponse>('/payment', body).then((r) => r.data),
  update: (id: number, body: UpdatePaymentRequest) =>
    api.put<PaymentResponse>(`/payment/${id}`, body).then((r) => r.data),
  remove: (id: number) =>
    api.delete<DeletePaymentResponse>(`/payment/${id}`).then((r) => r.data),
};
