package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CreateOrderItemRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderItemResponse;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.OrderItem;
import co.edu.usbcali.ecommerceusb.model.Product;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class OrderItemMapper {

    public static OrderItem requestToModel(CreateOrderItemRequest request, Order order, Product product) {
        return OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(request.getQuantity())
                .unitPriceSnapshot(request.getUnitPriceSnapshot())
                // Calculamos el total de la línea: precio * cantidad
                .lineTotal(request.getUnitPriceSnapshot().multiply(new BigDecimal(request.getQuantity())))
                .createdAt(OffsetDateTime.now())
                .build();
    }

    public static OrderItemResponse modelToResponse(OrderItem model) {
        return OrderItemResponse.builder()
                .id(model.getId())
                .orderId(model.getOrder().getId())
                .productId(model.getProduct().getId())
                .productName(model.getProduct().getName())
                .quantity(model.getQuantity())
                .unitPriceSnapshot(model.getUnitPriceSnapshot())
                .lineTotal(model.getLineTotal())
                .createdAt(model.getCreatedAt())
                .build();
    }
}