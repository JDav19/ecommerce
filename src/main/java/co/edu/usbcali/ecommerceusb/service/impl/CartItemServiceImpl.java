package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CartItemResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCartItemRequest;
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

        // 1. Buscamos si el producto ya está en ese carrito
        List<CartItem> existingItems = cartItemRepository.findByCartId(request.getCartId());
        Optional<CartItem> existingItem = existingItems.stream()
                .filter(item -> item.getProduct().getId().equals(request.getProductId()))
                .findFirst();

        CartItem cartItem;
        if (existingItem.isPresent()) {
            // 2. Si ya existe, sumamos la cantidad
            cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
            cartItem.setUpdatedAt(OffsetDateTime.now());
        } else {
            // 3. Si no existe, lo creamos de cero
            cartItem = CartItemMapper.createCartItemRequestToCartItem(request, cart, product);
        }

        return CartItemMapper.modelToCartItemResponse(cartItemRepository.save(cartItem));
    }
}