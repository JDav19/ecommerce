package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.UpdateCartRequest;
import co.edu.usbcali.ecommerceusb.dto.response.CartResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateCartRequest;
import java.util.List;

public interface CartService {
    List<CartResponse> getAllCarts();
    CartResponse getCartById(Integer id) throws Exception;
    CartResponse createCart(CreateCartRequest request) throws Exception;
    CartResponse updateCart(Integer id, UpdateCartRequest request) throws Exception;
}