package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.request.CreateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.dto.response.InventoryMovementResponse;
import co.edu.usbcali.ecommerceusb.model.InventoryMovement;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.Product;

import java.time.OffsetDateTime;
import java.util.List;

public class InventoryMovementMapper {

    public static InventoryMovementResponse modelToResponse(InventoryMovement movement) {
        return InventoryMovementResponse.builder()
                .id(movement.getId())
                .productId(movement.getProduct() != null ? movement.getProduct().getId() : null)
                .productName(movement.getProduct() != null ? movement.getProduct().getName() : null)
                .orderId(movement.getOrder() != null ? movement.getOrder().getId() : null)
                .type(movement.getType())
                .qty(movement.getQty())
                .createdAt(movement.getCreatedAt())
                .build();
    }

    public static List<InventoryMovementResponse> modelToResponseList(List<InventoryMovement> movements) {
        return movements.stream().map(InventoryMovementMapper::modelToResponse).toList();
    }

    public static InventoryMovement requestToModel(CreateInventoryMovementRequest request,
                                                   Product product, Order order) {
        return InventoryMovement.builder()
                .product(product)
                .order(order)
                .type("DEBIT")
                .qty(request.getQty())
                .createdAt(OffsetDateTime.now())
                .build();
    }
}