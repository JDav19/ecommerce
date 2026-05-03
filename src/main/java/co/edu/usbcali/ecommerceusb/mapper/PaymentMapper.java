package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CreatePaymentRequest;
import co.edu.usbcali.ecommerceusb.dto.PaymentResponse;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.Payment;

import java.time.OffsetDateTime;

public class PaymentMapper {

    public static Payment requestToModel(CreatePaymentRequest request, Order order) {
        return Payment.builder()
                .order(order)
                .status("SUCCEEDED")
                .providerRef(request.getProviderRef())
                .idempotencyKey(request.getIdempotencyKey())
                .createdAt(OffsetDateTime.now())
                .build();
    }

    public static PaymentResponse modelToResponse(Payment model) {
        return PaymentResponse.builder()
                .id(model.getId())
                .orderId(model.getOrder().getId())
                .status(model.getStatus())
                .providerRef(model.getProviderRef())
                .idempotencyKey(model.getIdempotencyKey())
                .createdAt(model.getCreatedAt())
                .build();
    }
}