package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.request.CreateOrderItemRequest;
import co.edu.usbcali.ecommerceusb.dto.request.UpdateOrderItemRequest;
import co.edu.usbcali.ecommerceusb.dto.response.OrderItemResponse;
import java.util.List;

public interface OrderItemService {
    List<OrderItemResponse> findAll();
    OrderItemResponse findById(Integer id);
    OrderItemResponse create(CreateOrderItemRequest request);
    OrderItemResponse update(Integer id, UpdateOrderItemRequest request);
}