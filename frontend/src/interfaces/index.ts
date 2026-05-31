// ─── User ────────────────────────────────────────────────────────────────────
export interface UserResponse {
  id: number;
  fullName: string;
  email: string;
  documentTypeId: number;
  documentTypeName: string;
  documentNumber: string;
}
export interface CreateUserRequest {
  fullName: string;
  phone: string;
  email: string;
  documentTypeId: number;
  documentNumber: string;
  birthDate: string;
  country: string;
  address: string;
}
export interface UpdateUserRequest {
  fullName?: string;
  phone?: string;
  address?: string;
  country?: string;
}
export interface DeleteUserResponse { message: string; }

// ─── Product ─────────────────────────────────────────────────────────────────
export interface ProductResponse {
  id: number;
  name: string;
  description: string;
  price: number;
  available: boolean;
}
export interface CreateProductRequest {
  name: string;
  description: string;
  price: number;
  available: boolean;
}
export interface UpdateProductRequest {
  name?: string;
  description?: string;
  price?: number;
  available?: boolean;
}
export interface DeleteProductResponse { message: string; }

// ─── Category ────────────────────────────────────────────────────────────────
export interface CategoryResponse {
  id: number;
  name: string;
  parentId: number;
  parentName: string;
}
export interface CreateCategoryRequest {
  name: string;
  parentId?: number;
}
export interface UpdateCategoryRequest {
  name?: string;
  parentId?: number;
}
export interface DeleteCategoryResponse { message: string; }

// ─── ProductCategory ─────────────────────────────────────────────────────────
export interface ProductCategoryResponse {
  id: number;
  productId: number;
  productName: string;
  categoryId: number;
  categoryName: string;
}
export interface CreateProductCategoryRequest {
  productId: number;
  categoryId: number;
}
export interface UpdateProductCategoryRequest {
  productId?: number;
  categoryId?: number;
}
export interface DeleteProductCategoryResponse { message: string; }

// ─── Cart ─────────────────────────────────────────────────────────────────────
export interface CartResponse {
  id: number;
  userId: number;
  userEmail: string;
  status: string;
}
export interface CreateCartRequest {
  userId: number;
  status: string;
}
export interface UpdateCartRequest {
  userId?: number;
  status?: string;
}
export interface DeleteCartResponse { message: string; }

// ─── CartItem ─────────────────────────────────────────────────────────────────
export interface CartItemResponse {
  id: number;
  cartId: number;
  productId: number;
  productName: string;
  quantity: number;
}
export interface CreateCartItemRequest {
  cartId: number;
  productId: number;
  quantity: number;
}
export interface UpdateCartItemRequest {
  quantity: number;
}
export interface DeleteCartItemResponse { message: string; }

// ─── Order ────────────────────────────────────────────────────────────────────
export interface OrderResponse {
  id: number;
  userId: number;
  userName: string;
  status: string;
  totalAmount: number;
  currency: string;
  createdAt: string;
}
export interface CreateOrderRequest {
  userId: number;
  totalAmount: number;
  currency: string;
}
export interface UpdateOrderRequest {
  status?: string;
  totalAmount?: number;
  currency?: string;
}
export interface DeleteOrderResponse { message: string; }

// ─── OrderItem ────────────────────────────────────────────────────────────────
export interface OrderItemResponse {
  id: number;
  orderId: number;
  productId: number;
  productName: string;
  quantity: number;
  unitPriceSnapshot: number;
  lineTotal: number;
  createdAt: string;
}
export interface CreateOrderItemRequest {
  orderId: number;
  productId: number;
  quantity: number;
  unitPriceSnapshot: number;
}
export interface UpdateOrderItemRequest {
  quantity: number;
}
export interface DeleteOrderItemResponse { message: string; }

// ─── Payment ─────────────────────────────────────────────────────────────────
export interface PaymentResponse {
  id: number;
  orderId: number;
  status: string;
  providerRef: string;
  idempotencyKey: string;
  createdAt: string;
}
export interface CreatePaymentRequest {
  orderId: number;
  providerRef: string;
  idempotencyKey: string;
}
export interface UpdatePaymentRequest {
  status?: string;
  providerRef?: string;
}
export interface DeletePaymentResponse { message: string; }

// ─── Inventory ────────────────────────────────────────────────────────────────
export interface InventoryResponse {
  id: number;
  productId: number;
  productName: string;
  stock: number;
}
export interface CreateInventoryRequest {
  productId: number;
  stock: number;
}
export interface UpdateInventoryRequest {
  stock: number;
}
export interface DeleteInventoryResponse { message: string; }

// ─── InventoryMovement ────────────────────────────────────────────────────────
export interface InventoryMovementResponse {
  id: number;
  productId: number;
  productName: string;
  orderId: number;
  type: string;
  qty: number;
  createdAt: string;
}
export interface CreateInventoryMovementRequest {
  productId: number;
  orderId: number;
  type: string;
  qty: number;
}
export interface UpdateInventoryMovementRequest {
  type?: string;
  qty?: number;
}
export interface DeleteInventoryMovementResponse { message: string; }

// ─── DocumentType ─────────────────────────────────────────────────────────────
export interface DocumentTypeResponse {
  id: number;
  code: string;
  name: string;
}
export interface CreateDocumentTypeRequest {
  code: string;
  name: string;
}
export interface UpdateDocumentTypeRequest {
  code?: string;
  name?: string;
}
export interface DeleteDocumentTypeResponse { message: string; }
