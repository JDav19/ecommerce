package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.CreateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.dto.InventoryMovementResponse;

import java.util.List;

public interface InventoryMovementService {
    List<InventoryMovementResponse> getMovements();
    InventoryMovementResponse getMovementById(Integer id) throws Exception;
    InventoryMovementResponse createMovement(CreateInventoryMovementRequest request) throws Exception;
}