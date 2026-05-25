package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.request.UpdateCartItemRequest;
import co.edu.usbcali.ecommerceusb.dto.response.CartItemResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateCartItemRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteCartItemResponse;
import co.edu.usbcali.ecommerceusb.mapper.CartItemMapper;
import co.edu.usbcali.ecommerceusb.model.Cart;
import co.edu.usbcali.ecommerceusb.model.CartItem;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.CartItemRepository;
import co.edu.usbcali.ecommerceusb.repository.CartRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import co.edu.usbcali.ecommerceusb.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<CartItemResponse> getItemsByCartId(Integer cartId) {
        return CartItemMapper.modelToCartItemResponseList(cartItemRepository.findByCartId(cartId));
    }

    @Override
    public List<CartItemResponse> getAllItems() {
        return CartItemMapper.modelToCartItemResponseList(cartItemRepository.findAll());
    }

    @Override
    public CartItemResponse addProductToCart(CreateCartItemRequest request) throws Exception {
        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new Exception("El carrito no existe"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new Exception("El producto no existe"));

        List<CartItem> existingItems = cartItemRepository.findByCartId(request.getCartId());
        Optional<CartItem> existingItem = existingItems.stream()
                .filter(item -> item.getProduct().getId().equals(request.getProductId()))
                .findFirst();

        CartItem cartItem;
        if (existingItem.isPresent()) {
            cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
            cartItem.setUpdatedAt(OffsetDateTime.now());
        } else {
            cartItem = CartItemMapper.createCartItemRequestToCartItem(request, cart, product);
        }

        return CartItemMapper.modelToCartItemResponse(cartItemRepository.save(cartItem));
    }

    @Override
    public CartItemResponse updateQuantity(Integer id, UpdateCartItemRequest request) throws Exception {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new Exception("El item del carrito no existe"));

        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new Exception("La cantidad debe ser mayor a cero");
        }

        CartItemMapper.updateCartItemFromRequest(cartItem, request);

        return CartItemMapper.modelToCartItemResponse(cartItemRepository.save(cartItem));
    }

    @Override
    public DeleteCartItemResponse deleteCartItem(Integer id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("Ingrese ID para eliminar");
        }
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() ->
                        new Exception(String.format("No se encontró el item del carrito con id %d", id)));
        cartItemRepository.delete(cartItem);
        return DeleteCartItemResponse.builder()
                .message(String.format("Item del carrito con id %d eliminado con éxito", id))
                .build();
    }
}
