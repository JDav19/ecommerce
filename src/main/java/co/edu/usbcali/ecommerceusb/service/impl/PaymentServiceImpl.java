package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.request.CreatePaymentRequest;
import co.edu.usbcali.ecommerceusb.dto.request.UpdatePaymentRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeletePaymentResponse;
import co.edu.usbcali.ecommerceusb.dto.response.PaymentResponse;
import co.edu.usbcali.ecommerceusb.exception.BadRequestException;
import co.edu.usbcali.ecommerceusb.exception.ResourceNotFoundException;
import co.edu.usbcali.ecommerceusb.mapper.PaymentMapper;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.Payment;
import co.edu.usbcali.ecommerceusb.repository.OrderRepository;
import co.edu.usbcali.ecommerceusb.repository.PaymentRepository;
import co.edu.usbcali.ecommerceusb.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<PaymentResponse> findAll() {
        return paymentRepository.findAll().stream()
                .map(PaymentMapper::modelToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponse findById(Integer id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con ID: " + id));
        return PaymentMapper.modelToResponse(payment);
    }

    @Override
    public PaymentResponse create(CreatePaymentRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada para el pago"));

        Payment payment = PaymentMapper.requestToModel(request, order);
        return PaymentMapper.modelToResponse(paymentRepository.save(payment));
    }

    @Override
    public PaymentResponse update(Integer id, UpdatePaymentRequest request) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con ID: " + id));
        if (request.getStatus() == null || request.getStatus().isBlank()) {
            throw new BadRequestException("El estado del pago no puede estar vacío");
        }
        PaymentMapper.updateModelFromRequest(payment, request);
        return PaymentMapper.modelToResponse(paymentRepository.save(payment));
    }

    @Override
    public DeletePaymentResponse deletePayment(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Ingrese ID para eliminar");
        }
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("No se encontró el pago con id %d", id)));
        paymentRepository.delete(payment);
        return DeletePaymentResponse.builder()
                .message(String.format("Pago con id %d borrado con éxito", id))
                .build();
    }
}