package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.request.UpdateCartRequest;
import co.edu.usbcali.ecommerceusb.dto.response.CartResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateCartRequest;
import co.edu.usbcali.ecommerceusb.mapper.CartMapper;
import co.edu.usbcali.ecommerceusb.model.Cart;
import co.edu.usbcali.ecommerceusb.model.User;
import co.edu.usbcali.ecommerceusb.repository.CartRepository;
import co.edu.usbcali.ecommerceusb.repository.UserRepository;
import co.edu.usbcali.ecommerceusb.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CartResponse> getAllCarts() {
        return CartMapper.modelToCartResponseList(cartRepository.findAll());
    }

    @Override
    public CartResponse getCartById(Integer id) throws Exception {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));
        return CartMapper.modelToCartResponse(cart);
    }

    @Override
    public CartResponse createCart(CreateCartRequest request) throws Exception {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new Exception("El usuario no existe para asociar al carrito"));

        Cart cart = CartMapper.createCartRequestToCart(request, user);
        return CartMapper.modelToCartResponse(cartRepository.save(cart));
    }

    @Override
    public CartResponse updateCart(Integer id, UpdateCartRequest request) throws Exception {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new Exception("El carrito a actualizar no existe"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new Exception("El usuario no existe para asociar al carrito"));

        CartMapper.updateCartFromRequest(cart, request, user);

        return CartMapper.modelToCartResponse(cartRepository.save(cart));
    }
}