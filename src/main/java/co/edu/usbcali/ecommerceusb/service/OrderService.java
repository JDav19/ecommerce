package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.request.CreateOrderRequest;
import co.edu.usbcali.ecommerceusb.dto.request.UpdateOrderRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteOrderResponse;
import co.edu.usbcali.ecommerceusb.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getOrders();
    OrderResponse getOrderById(Integer id);
    OrderResponse createOrder(CreateOrderRequest request);
    OrderResponse updateOrder(Integer id, UpdateOrderRequest request);
    DeleteOrderResponse deleteOrder(Integer id);
}