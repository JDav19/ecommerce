package co.edu.usbcali.ecommerceusb.controller;

import co.edu.usbcali.ecommerceusb.dto.request.UpdateCartItemRequest;
import co.edu.usbcali.ecommerceusb.dto.response.CartItemResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateCartItemRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteCartItemResponse;
import co.edu.usbcali.ecommerceusb.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-item")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/cart/{cartId}")
    public List<CartItemResponse> getByCart(@PathVariable Integer cartId) {
        return cartItemService.getItemsByCartId(cartId);
    }

    @GetMapping("/all")
    public List<CartItemResponse> getAll() {
        return cartItemService.getAllItems();
    }

    @PostMapping
    public ResponseEntity<CartItemResponse> addProduct(@RequestBody CreateCartItemRequest request) {
        return new ResponseEntity<>(cartItemService.addProductToCart(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItemResponse> updateQuantity(@PathVariable Integer id,
                                                           @RequestBody UpdateCartItemRequest request) {
        return new ResponseEntity<>(cartItemService.updateQuantity(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteCartItemResponse> deleteCartItem(@PathVariable Integer id) {
        return new ResponseEntity<>(cartItemService.deleteCartItem(id), HttpStatus.OK);
    }
}
