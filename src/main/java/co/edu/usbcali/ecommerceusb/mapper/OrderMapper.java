package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CreateOrderRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderResponse;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.User;

import java.time.OffsetDateTime;
import java.util.List;

public class OrderMapper {

    public static OrderResponse modelToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser() != null ? order.getUser().getId() : null)
                .userName(order.getUser() != null ? order.getUser().getFullName() : null)
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .currency(order.getCurrency())
                .createdAt(order.getCreatedAt())
                .build();
    }

    public static List<OrderResponse> modelToResponseList(List<Order> orders) {
        return orders.stream().map(OrderMapper::modelToResponse).toList();
    }

    public static Order requestToModel(CreateOrderRequest request, User user) {
        return Order.builder()
                .user(user)
                .status("CREATED")
                .totalAmount(request.getTotalAmount())
                .currency(request.getCurrency())
                .createdAt(OffsetDateTime.now())
                .build();
    }
}