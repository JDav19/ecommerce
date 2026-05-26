package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.request.UpdateInventoryRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteInventoryResponse;
import co.edu.usbcali.ecommerceusb.dto.response.InventoryResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateInventoryRequest;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> getAllInventory();
    InventoryResponse getById(Integer id);
    InventoryResponse getByProductId(Integer productId);
    InventoryResponse updateStock(CreateInventoryRequest request);
    InventoryResponse updateInventory(Integer id, UpdateInventoryRequest request);
    DeleteInventoryResponse deleteInventory(Integer id);
}