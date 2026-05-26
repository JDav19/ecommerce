package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.request.UpdateCartRequest;
import co.edu.usbcali.ecommerceusb.dto.response.CartResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateCartRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteCartResponse;
import co.edu.usbcali.ecommerceusb.exception.BadRequestException;
import co.edu.usbcali.ecommerceusb.exception.ResourceNotFoundException;
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
    public CartResponse getCartById(Integer id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado"));
        return CartMapper.modelToCartResponse(cart);
    }

    @Override
    public CartResponse createCart(CreateCartRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe para asociar al carrito"));

        Cart cart = CartMapper.createCartRequestToCart(request, user);
        return CartMapper.modelToCartResponse(cartRepository.save(cart));
    }

    @Override
    public CartResponse updateCart(Integer id, UpdateCartRequest request) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El carrito a actualizar no existe"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe para asociar al carrito"));

        CartMapper.updateCartFromRequest(cart, request, user);

        return CartMapper.modelToCartResponse(cartRepository.save(cart));
    }

    @Override
    public DeleteCartResponse deleteCart(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Ingrese ID para eliminar");
        }
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("No se encontró el carrito con id %d", id)));
        cartRepository.delete(cart);
        return DeleteCartResponse.builder()
                .message(String.format("Carrito con id %d eliminado con éxito", id))
                .build();
    }
}
