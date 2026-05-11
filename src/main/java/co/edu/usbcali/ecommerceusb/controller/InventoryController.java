package co.edu.usbcali.ecommerceusb.controller;

import co.edu.usbcali.ecommerceusb.dto.response.InventoryResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateInventoryRequest;
import co.edu.usbcali.ecommerceusb.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/all")
    public List<InventoryResponse> getAll() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/{id}")
    public InventoryResponse getById(@PathVariable("id") Integer id) throws Exception {
        return inventoryService.getById(id);
    }

    @GetMapping("/product/{productId}")
    public InventoryResponse getByProductId(@PathVariable("productId") Integer productId) throws Exception {
        return inventoryService.getByProductId(productId);
    }

    @PostMapping
    public InventoryResponse updateStock(@RequestBody CreateInventoryRequest request) throws Exception {
        return inventoryService.updateStock(request);
    }
}