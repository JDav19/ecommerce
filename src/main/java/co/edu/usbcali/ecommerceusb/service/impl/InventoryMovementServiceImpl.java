package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.request.CreateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.dto.request.UpdateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.dto.response.InventoryMovementResponse;
import co.edu.usbcali.ecommerceusb.mapper.InventoryMovementMapper;
import co.edu.usbcali.ecommerceusb.model.InventoryMovement;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.InventoryMovementRepository;
import co.edu.usbcali.ecommerceusb.repository.OrderRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import co.edu.usbcali.ecommerceusb.service.InventoryMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class InventoryMovementServiceImpl implements InventoryMovementService {

    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<InventoryMovementResponse> getMovements() {
        List<InventoryMovement> movements = inventoryMovementRepository.findAll();
        if (movements.isEmpty()) {
            return List.of();
        }
        return InventoryMovementMapper.modelToResponseList(movements);
    }

    @Override
    public InventoryMovementResponse getMovementById(Integer id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("Debe ingresar el id para buscar el movimiento");
        }
        InventoryMovement movement = inventoryMovementRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Movimiento no encontrado con id: %d", id)));
        return InventoryMovementMapper.modelToResponse(movement);
    }

    @Override
    public InventoryMovementResponse createMovement(CreateInventoryMovementRequest request) throws Exception {
        if (Objects.isNull(request)) {
            throw new Exception("El objeto CreateInventoryMovementRequest no puede ser nulo");
        }

        if (request.getProductId() == null || request.getProductId() <= 0) {
            throw new Exception("El campo productId debe ser válido");
        }

        if (request.getOrderId() == null || request.getOrderId() <= 0) {
            throw new Exception("El campo orderId debe ser válido");
        }

        if (request.getType() == null || request.getType().isBlank()) {
            throw new Exception("El campo type no puede ser nulo o vacío");
        }

        if (request.getQty() == null || request.getQty() <= 0) {
            throw new Exception("La cantidad (qty) debe ser mayor a 0");
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new Exception("El producto especificado no existe"));

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new Exception("La orden especificada no existe"));

        InventoryMovement movement = InventoryMovementMapper.requestToModel(request, product, order);
        movement = inventoryMovementRepository.save(movement);

        return InventoryMovementMapper.modelToResponse(movement);
    }

    @Override
    public InventoryMovementResponse updateMovement(Integer id, UpdateInventoryMovementRequest request) throws Exception {
        InventoryMovement movement = inventoryMovementRepository.findById(id)
                .orElseThrow(() -> new Exception("Movimiento de inventario no encontrado"));
        if (request.getQty() == null || request.getQty() <= 0) {
            throw new Exception("La cantidad debe ser mayor a cero");
        }
        if (request.getType() == null || request.getType().isBlank()) {
            throw new Exception("El tipo de movimiento no puede estar vacío");
        }
        InventoryMovementMapper.updateModelFromRequest(movement, request);
        return InventoryMovementMapper.modelToResponse(inventoryMovementRepository.save(movement));
    }
}