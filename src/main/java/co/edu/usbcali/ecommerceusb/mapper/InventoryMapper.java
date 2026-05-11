package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.request.UpdateInventoryRequest;
import co.edu.usbcali.ecommerceusb.dto.response.InventoryResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateInventoryRequest;
import co.edu.usbcali.ecommerceusb.model.Inventory;
import co.edu.usbcali.ecommerceusb.model.Product;

import java.time.OffsetDateTime;
import java.util.List;

public class InventoryMapper {

    public static InventoryResponse modelToInventoryResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .id(inventory.getId())
                .productId(inventory.getProduct().getId())
                .productName(inventory.getProduct().getName())
                .stock(inventory.getStock())
                .build();
    }

    public static List<InventoryResponse> modelToInventoryResponseList(List<Inventory> inventories) {
        return inventories.stream().map(InventoryMapper::modelToInventoryResponse).toList();
    }

    public static Inventory createRequestToModel(CreateInventoryRequest request, Product product) {
        return Inventory.builder()
                .product(product)
                .stock(request.getStock())
                .updatedAt(OffsetDateTime.now())
                .build();
    }

    public static void updateInventoryFromRequest(Inventory inventory, UpdateInventoryRequest request) {
        inventory.setStock(request.getStock());
        inventory.setUpdatedAt(OffsetDateTime.now());
    }
}