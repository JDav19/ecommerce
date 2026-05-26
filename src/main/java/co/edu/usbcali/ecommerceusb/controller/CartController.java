package co.edu.usbcali.ecommerceusb.controller;

import co.edu.usbcali.ecommerceusb.dto.request.UpdateCartRequest;
import co.edu.usbcali.ecommerceusb.dto.response.CartResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateCartRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteCartResponse;
import co.edu.usbcali.ecommerceusb.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/all")
    public List<CartResponse> getAll() {
        return cartService.getAllCarts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getById(@PathVariable Integer id) {
        return new ResponseEntity<>(cartService.getCartById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CartResponse> create(@RequestBody CreateCartRequest request) {
        return new ResponseEntity<>(cartService.createCart(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartResponse> update(@PathVariable Integer id,
                                               @RequestBody UpdateCartRequest request) {
        return new ResponseEntity<>(cartService.updateCart(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteCartResponse> deleteCart(@PathVariable Integer id) {
        return new ResponseEntity<>(cartService.deleteCart(id), HttpStatus.OK);
    }
}
