package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.request.CreateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.dto.request.UpdateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.dto.response.InventoryMovementResponse;

import java.util.List;

public interface InventoryMovementService {
    List<InventoryMovementResponse> getMovements();
    InventoryMovementResponse getMovementById(Integer id) throws Exception;
    InventoryMovementResponse createMovement(CreateInventoryMovementRequest request) throws Exception;
    InventoryMovementResponse updateMovement(Integer id, UpdateInventoryMovementRequest request) throws Exception;
}