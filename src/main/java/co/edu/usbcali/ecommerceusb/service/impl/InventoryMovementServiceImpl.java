package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.request.CreateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.dto.request.UpdateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteInventoryMovementResponse;
import co.edu.usbcali.ecommerceusb.dto.response.InventoryMovementResponse;
import co.edu.usbcali.ecommerceusb.exception.BadRequestException;
import co.edu.usbcali.ecommerceusb.exception.ResourceNotFoundException;
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
    public InventoryMovementResponse getMovementById(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para buscar el movimiento");
        }
        InventoryMovement movement = inventoryMovementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Movimiento no encontrado con id: %d", id)));
        return InventoryMovementMapper.modelToResponse(movement);
    }

    @Override
    public InventoryMovementResponse createMovement(CreateInventoryMovementRequest request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException("El objeto CreateInventoryMovementRequest no puede ser nulo");
        }

        if (request.getProductId() == null || request.getProductId() <= 0) {
            throw new BadRequestException("El campo productId debe ser válido");
        }

        if (request.getOrderId() == null || request.getOrderId() <= 0) {
            throw new BadRequestException("El campo orderId debe ser válido");
        }

        if (request.getType() == null || request.getType().isBlank()) {
            throw new BadRequestException("El campo type no puede ser nulo o vacío");
        }

        if (request.getQty() == null || request.getQty() <= 0) {
            throw new BadRequestException("La cantidad (qty) debe ser mayor a 0");
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("El producto especificado no existe"));

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("La orden especificada no existe"));

        InventoryMovement movement = InventoryMovementMapper.requestToModel(request, product, order);
        movement = inventoryMovementRepository.save(movement);

        return InventoryMovementMapper.modelToResponse(movement);
    }

    @Override
    public InventoryMovementResponse updateMovement(Integer id, UpdateInventoryMovementRequest request) {
        InventoryMovement movement = inventoryMovementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento de inventario no encontrado"));
        if (request.getQty() == null || request.getQty() <= 0) {
            throw new BadRequestException("La cantidad debe ser mayor a cero");
        }
        if (request.getType() == null || request.getType().isBlank()) {
            throw new BadRequestException("El tipo de movimiento no puede estar vacío");
        }
        InventoryMovementMapper.updateModelFromRequest(movement, request);
        return InventoryMovementMapper.modelToResponse(inventoryMovementRepository.save(movement));
    }

    @Override
    public DeleteInventoryMovementResponse deleteInventoryMovement(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Ingrese ID para eliminar");
        }
        InventoryMovement inventoryMovement = inventoryMovementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("No se encontró el movimiento de inventario con id %d", id)));
        inventoryMovementRepository.delete(inventoryMovement);
        return DeleteInventoryMovementResponse.builder()
                .message(String.format("Movimiento de inventario con id %d borrado con éxito", id))
                .build();
    }
}
