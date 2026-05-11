package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.request.UpdateCartItemRequest;
import co.edu.usbcali.ecommerceusb.dto.response.CartItemResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateCartItemRequest;
import co.edu.usbcali.ecommerceusb.model.Cart;
import co.edu.usbcali.ecommerceusb.model.CartItem;
import co.edu.usbcali.ecommerceusb.model.Product;

import java.time.OffsetDateTime;
import java.util.List;

public class CartItemMapper {

    public static CartItemResponse modelToCartItemResponse(CartItem cartItem) {
        return CartItemResponse.builder()
                .id(cartItem.getId())
                .cartId(cartItem.getCart().getId())
                .productId(cartItem.getProduct().getId())
                .productName(cartItem.getProduct().getName())
                .quantity(cartItem.getQuantity())
                .build();
    }

    public static List<CartItemResponse> modelToCartItemResponseList(List<CartItem> cartItems) {
        return cartItems.stream().map(CartItemMapper::modelToCartItemResponse).toList();
    }

    public static CartItem createCartItemRequestToCartItem(CreateCartItemRequest request, Cart cart, Product product) {
        return CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(request.getQuantity())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();
    }

    public static void updateCartItemFromRequest(CartItem cartItem, UpdateCartItemRequest request) {
        cartItem.setQuantity(request.getQuantity());
        cartItem.setUpdatedAt(OffsetDateTime.now());
    }
}