package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.request.CreateOrderItemRequest;
import co.edu.usbcali.ecommerceusb.dto.request.UpdateOrderItemRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteOrderItemResponse;
import co.edu.usbcali.ecommerceusb.dto.response.OrderItemResponse;
import co.edu.usbcali.ecommerceusb.exception.BadRequestException;
import co.edu.usbcali.ecommerceusb.exception.ResourceNotFoundException;
import co.edu.usbcali.ecommerceusb.mapper.OrderItemMapper;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.OrderItem;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.OrderItemRepository;
import co.edu.usbcali.ecommerceusb.repository.OrderRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import co.edu.usbcali.ecommerceusb.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public List<OrderItemResponse> findAll() {
        return orderItemRepository.findAll().stream()
                .map(OrderItemMapper::modelToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemResponse findById(Integer id) {
        OrderItem item = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem no encontrado con ID: " + id));
        return OrderItemMapper.modelToResponse(item);
    }

    @Override
    public OrderItemResponse create(CreateOrderItemRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        OrderItem newItem = OrderItemMapper.requestToModel(request, order, product);
        return OrderItemMapper.modelToResponse(orderItemRepository.save(newItem));
    }

    @Override
    public OrderItemResponse update(Integer id, UpdateOrderItemRequest request) {
        OrderItem item = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem no encontrado con ID: " + id));
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new BadRequestException("La cantidad debe ser mayor a cero");
        }
        OrderItemMapper.updateModelFromRequest(item, request);
        return OrderItemMapper.modelToResponse(orderItemRepository.save(item));
    }

    @Override
    public DeleteOrderItemResponse deleteOrderItem(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Ingrese ID para eliminar");
        }
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("No se encontró el OrderItem con id %d", id)));
        orderItemRepository.delete(orderItem);
        return DeleteOrderItemResponse.builder()
                .message(String.format("OrderItem con id %d borrado correctamente", id))
                .build();
    }
}