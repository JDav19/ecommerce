package co.edu.usbcali.ecommerceusb.controller;

import co.edu.usbcali.ecommerceusb.dto.request.UpdateInventoryRequest;
import co.edu.usbcali.ecommerceusb.dto.response.InventoryResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateInventoryRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteInventoryResponse;
import co.edu.usbcali.ecommerceusb.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public InventoryResponse getById(@PathVariable("id") Integer id) {
        return inventoryService.getById(id);
    }

    @GetMapping("/product/{productId}")
    public InventoryResponse getByProductId(@PathVariable("productId") Integer productId) {
        return inventoryService.getByProductId(productId);
    }

    @PostMapping
    public InventoryResponse updateStock(@RequestBody CreateInventoryRequest request) {
        return inventoryService.updateStock(request);
    }

    @PutMapping("/{id}")
    public InventoryResponse update(@PathVariable Integer id,
                                    @RequestBody UpdateInventoryRequest request) {
        return inventoryService.updateInventory(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteInventoryResponse> deleteInventory(@PathVariable Integer id) {
        return new ResponseEntity<>(inventoryService.deleteInventory(id), HttpStatus.OK);
    }
}
