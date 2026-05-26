package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.request.UpdateInventoryRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteInventoryResponse;
import co.edu.usbcali.ecommerceusb.dto.response.InventoryResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateInventoryRequest;
import co.edu.usbcali.ecommerceusb.exception.BadRequestException;
import co.edu.usbcali.ecommerceusb.exception.ResourceNotFoundException;
import co.edu.usbcali.ecommerceusb.mapper.InventoryMapper;
import co.edu.usbcali.ecommerceusb.model.Inventory;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.InventoryRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import co.edu.usbcali.ecommerceusb.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<InventoryResponse> getAllInventory() {
        return InventoryMapper.modelToInventoryResponseList(inventoryRepository.findAll());
    }

    @Override
    public InventoryResponse getById(Integer id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un registro de inventario con ese ID"));
        return InventoryMapper.modelToInventoryResponse(inventory);
    }

    @Override
    public InventoryResponse getByProductId(Integer productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("No hay registro de inventario para este producto"));
        return InventoryMapper.modelToInventoryResponse(inventory);
    }

    @Override
    public InventoryResponse updateStock(CreateInventoryRequest request) {
        Inventory inventory = inventoryRepository.findByProductId(request.getProductId())
                .orElse(null);

        if (inventory != null) {
            inventory.setStock(request.getStock());
            inventory.setUpdatedAt(OffsetDateTime.now());
        } else {
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("El producto no existe"));
            inventory = InventoryMapper.createRequestToModel(request, product);
        }

        return InventoryMapper.modelToInventoryResponse(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponse updateInventory(Integer id, UpdateInventoryRequest request) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El registro de inventario no existe"));
        if (request.getStock() < 0) {
            throw new BadRequestException("El stock no puede ser menor a cero");
        }
        InventoryMapper.updateInventoryFromRequest(inventory, request);
        return InventoryMapper.modelToInventoryResponse(inventoryRepository.save(inventory));
    }

    @Override
    public DeleteInventoryResponse deleteInventory(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Ingrese ID para eliminar");
        }
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("No se encontró el inventario con id %d", id)));
        inventoryRepository.delete(inventory);
        return DeleteInventoryResponse.builder()
                .message(String.format("Inventario con id %d removido exitosamente", id))
                .build();
    }
}