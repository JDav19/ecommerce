package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.request.CreateOrderRequest;
import co.edu.usbcali.ecommerceusb.dto.response.OrderResponse;
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
    public OrderResponse getOrderById(Integer id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("Debe ingresar un ID válido para buscar la orden");
        }
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Orden no encontrada con el id: %d", id)));
        return OrderMapper.modelToResponse(order);
    }

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) throws Exception {
        if (Objects.isNull(request)) throw new Exception("El request no puede ser nulo");

        // Validar integridad del usuario
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new Exception("El usuario no existe"));

        Order order = OrderMapper.requestToModel(request, user);
        return OrderMapper.modelToResponse(orderRepository.save(order));
    }
}