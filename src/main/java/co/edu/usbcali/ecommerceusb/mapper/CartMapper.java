package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.UpdateCartRequest;
import co.edu.usbcali.ecommerceusb.dto.response.CartResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateCartRequest;
import co.edu.usbcali.ecommerceusb.model.Cart;
import co.edu.usbcali.ecommerceusb.model.User;

import java.time.OffsetDateTime;
import java.util.List;

public class CartMapper {

    public static CartResponse modelToCartResponse(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .userId(cart.getUser() != null ? cart.getUser().getId() : null)
                .userEmail(cart.getUser() != null ? cart.getUser().getEmail() : null)
                .status(cart.getStatus())
                .build();
    }

    public static List<CartResponse> modelToCartResponseList(List<Cart> carts) {
        return carts.stream().map(CartMapper::modelToCartResponse).toList();
    }

    public static Cart createCartRequestToCart(CreateCartRequest request, User user) {
        return Cart.builder()
                .user(user)
                .status(request.getStatus())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();
    }

    public static void updateCartFromRequest(Cart cart, UpdateCartRequest request, User user) {
        cart.setUser(user);
        cart.setStatus(request.getStatus());
        cart.setUpdatedAt(OffsetDateTime.now());
    }
}