package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.request.CreateOrderRequest;
import co.edu.usbcali.ecommerceusb.dto.request.UpdateOrderRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteOrderResponse;
import co.edu.usbcali.ecommerceusb.dto.response.OrderResponse;
import co.edu.usbcali.ecommerceusb.exception.BadRequestException;
import co.edu.usbcali.ecommerceusb.exception.ResourceNotFoundException;
import co.edu.usbcali.ecommerceusb.mapper.OrderMapper;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.User;
import co.edu.usbcali.ecommerceusb.repository.OrderRepository;
import co.edu.usbcali.ecommerceusb.repository.UserRepository;
import co.edu.usbcali.ecommerceusb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<OrderResponse> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.isEmpty() ? List.of() : OrderMapper.modelToResponseList(orders);
    }

    @Override
    public OrderResponse getOrderById(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar un ID válido para buscar la orden");
        }
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Orden no encontrada con el id: %d", id)));
        return OrderMapper.modelToResponse(order);
    }

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        if (Objects.isNull(request)) throw new BadRequestException("El request no puede ser nulo");

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe"));

        Order order = OrderMapper.requestToModel(request, user);
        return OrderMapper.modelToResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse updateOrder(Integer id, UpdateOrderRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("La orden con ID %d no existe", id)));
        if (request.getTotalAmount() == null || request.getTotalAmount().compareTo(java.math.BigDecimal.ZERO) < 0) {
            throw new BadRequestException("El monto total no puede ser negativo o nulo");
        }
        OrderMapper.updateOrderFromRequest(order, request);
        return OrderMapper.modelToResponse(orderRepository.save(order));
    }

    @Override
    public DeleteOrderResponse deleteOrder(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Ingrese ID para eliminar");
        }
        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("No se encontró la orden con id %d", id)));
        orderRepository.delete(order);
        return DeleteOrderResponse.builder()
                .message(String.format("Orden con id %d eliminada con éxito", id))
                .build();
    }
}
