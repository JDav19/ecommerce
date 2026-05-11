package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.request.UpdateCartItemRequest;
import co.edu.usbcali.ecommerceusb.dto.response.CartItemResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateCartItemRequest;

import java.util.List;

public interface CartItemService {
    List<CartItemResponse> getItemsByCartId(Integer cartId);
    List<CartItemResponse> getAllItems();
    CartItemResponse addProductToCart(CreateCartItemRequest request) throws Exception;
    CartItemResponse updateQuantity(Integer id, UpdateCartItemRequest request) throws Exception;
}