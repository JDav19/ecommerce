import axios from 'axios';

export const API_URL = 'http://localhost:8080';

const api = axios.create({
  baseURL: API_URL,
  headers: { 'Content-Type': 'application/json' },
});

api.interceptors.response.use(
  (res) => res,
  (err) => {
    const msg = err.response?.data?.message ?? err.message ?? 'Error desconocido';
    return Promise.reject(new Error(msg));
  }
);

export default api;
