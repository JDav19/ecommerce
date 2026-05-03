package co.edu.usbcali.ecommerceusb.controller;

import co.edu.usbcali.ecommerceusb.dto.InventoryResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateInventoryRequest;
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

    // El que nos faltaba para buscar por el ID del inventario
    @GetMapping("/{id}")
    public InventoryResponse getById(@PathVariable("id") Integer id) throws Exception {
        return inventoryService.getById(id);
    }

    // También es muy útil buscar directamente por el ID del producto
    @GetMapping("/product/{productId}")
    public InventoryResponse getByProductId(@PathVariable("productId") Integer productId) throws Exception {
        return inventoryService.getByProductId(productId);
    }

    @PostMapping
    public InventoryResponse updateStock(@RequestBody CreateInventoryRequest request) throws Exception {
        return inventoryService.updateStock(request);
    }
}