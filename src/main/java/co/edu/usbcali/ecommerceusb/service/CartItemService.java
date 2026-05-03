package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.CartItemResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCartItemRequest;

import java.util.List;

public interface CartItemService {
    List<CartItemResponse> getItemsByCartId(Integer cartId);
    List<CartItemResponse> getAllItems();
    CartItemResponse addProductToCart(CreateCartItemRequest request) throws Exception;
}