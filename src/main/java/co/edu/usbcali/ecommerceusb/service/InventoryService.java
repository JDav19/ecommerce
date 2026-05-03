package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.InventoryResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateInventoryRequest;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> getAllInventory();
    InventoryResponse getById(Integer id) throws Exception;
    InventoryResponse getByProductId(Integer productId) throws Exception;
    InventoryResponse updateStock(CreateInventoryRequest request) throws Exception;
}