package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.InventoryResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateInventoryRequest;
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
        // Buscamos si ya existe registro para ese producto
        Inventory inventory = inventoryRepository.findByProductId(request.getProductId())
                .orElse(null);

        if (inventory != null) {
            // Si ya existe, actualizamos el stock
            inventory.setStock(request.getStock());
            inventory.setUpdatedAt(OffsetDateTime.now());
        } else {
            // Si no existe, buscamos el producto para crearlo
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new Exception("El producto no existe"));
            inventory = InventoryMapper.createRequestToModel(request, product);
        }

        return InventoryMapper.modelToInventoryResponse(inventoryRepository.save(inventory));
    }
}