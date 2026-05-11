package co.edu.usbcali.ecommerceusb.controller;

import co.edu.usbcali.ecommerceusb.dto.request.CreateOrderItemRequest;
import co.edu.usbcali.ecommerceusb.dto.request.UpdateOrderItemRequest;
import co.edu.usbcali.ecommerceusb.dto.response.OrderItemResponse;
import co.edu.usbcali.ecommerceusb.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-item")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping("/all")
    public ResponseEntity<List<OrderItemResponse>> getAll() {
        return ResponseEntity.ok(orderItemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderItemService.findById(id));
    }

    @PostMapping
    public ResponseEntity<OrderItemResponse> create(@RequestBody CreateOrderItemRequest request) {
        return new ResponseEntity<>(orderItemService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemResponse> update(@PathVariable Integer id,
                                                    @RequestBody UpdateOrderItemRequest request) {
        return ResponseEntity.ok(orderItemService.update(id, request));
    }
}