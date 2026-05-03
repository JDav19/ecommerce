package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.CreateOrderItemRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderItemResponse;
import java.util.List;

public interface OrderItemService {
    List<OrderItemResponse> findAll();
    OrderItemResponse findById(Integer id);
    OrderItemResponse create(CreateOrderItemRequest request);
}