package co.edu.usbcali.ecommerceusb.controller;

import co.edu.usbcali.ecommerceusb.dto.request.CreateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.dto.response.InventoryMovementResponse;
import co.edu.usbcali.ecommerceusb.service.InventoryMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory-movement")
public class InventoryMovementController {

    @Autowired
    private InventoryMovementService inventoryMovementService;

    @GetMapping("/all")
    public List<InventoryMovementResponse> getAll() {
        return inventoryMovementService.getMovements();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryMovementResponse> getById(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(inventoryMovementService.getMovementById(id),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InventoryMovementResponse> createMovement(
            @RequestBody CreateInventoryMovementRequest request) throws Exception {
        return new ResponseEntity<>(inventoryMovementService.createMovement(request),
                HttpStatus.CREATED);
    }
}