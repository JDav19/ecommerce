package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.request.UpdateCartRequest;
import co.edu.usbcali.ecommerceusb.dto.response.CartResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateCartRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteCartResponse;

import java.util.List;

public interface CartService {
    List<CartResponse> getAllCarts();
    CartResponse getCartById(Integer id);
    CartResponse createCart(CreateCartRequest request);
    CartResponse updateCart(Integer id, UpdateCartRequest request);
    DeleteCartResponse deleteCart(Integer id);
}
