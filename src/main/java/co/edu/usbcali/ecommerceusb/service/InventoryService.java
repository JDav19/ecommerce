package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.request.UpdateInventoryRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteInventoryResponse;
import co.edu.usbcali.ecommerceusb.dto.response.InventoryResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateInventoryRequest;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> getAllInventory();
    InventoryResponse getById(Integer id) throws Exception;
    InventoryResponse getByProductId(Integer productId) throws Exception;
    InventoryResponse updateStock(CreateInventoryRequest request) throws Exception;
    InventoryResponse updateInventory(Integer id, UpdateInventoryRequest request) throws Exception;
    DeleteInventoryResponse deleteInventory(Integer id) throws Exception;
}