package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.request.UpdateInventoryRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteInventoryResponse;
import co.edu.usbcali.ecommerceusb.dto.response.InventoryResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateInventoryRequest;
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
    public InventoryResponse getById(Integer id) throws Exception {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new Exception("No existe un registro de inventario con ese ID"));
        return InventoryMapper.modelToInventoryResponse(inventory);
    }

    @Override
    public InventoryResponse getByProductId(Integer productId) throws Exception {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new Exception("No hay registro de inventario para este producto"));
        return InventoryMapper.modelToInventoryResponse(inventory);
    }

    @Override
    public InventoryResponse updateStock(CreateInventoryRequest request) throws Exception {
        Inventory inventory = inventoryRepository.findByProductId(request.getProductId())
                .orElse(null);

        if (inventory != null) {
            inventory.setStock(request.getStock());
            inventory.setUpdatedAt(OffsetDateTime.now());
        } else {
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new Exception("El producto no existe"));
            inventory = InventoryMapper.createRequestToModel(request, product);
        }

        return InventoryMapper.modelToInventoryResponse(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponse updateInventory(Integer id, UpdateInventoryRequest request) throws Exception {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new Exception("El registro de inventario no existe"));
        if (request.getStock() < 0) {
            throw new Exception("El stock no puede ser menor a cero");
        }
        InventoryMapper.updateInventoryFromRequest(inventory, request);
        return InventoryMapper.modelToInventoryResponse(inventoryRepository.save(inventory));
    }

    @Override
    public DeleteInventoryResponse deleteInventory(Integer id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("Ingrese ID para eliminar");
        }
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new Exception(String.format("No se encontró el inventario con id %d", id)));
        inventoryRepository.delete(inventory);
        return DeleteInventoryResponse.builder()
                .message(String.format("Inventario con id %d removido exitosamente", id))
                .build();
    }
}