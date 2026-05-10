package co.edu.usbcali.ecommerceusb.dto.request;

import lombok.Data;

@Data
public class CreatePaymentRequest {
    private Integer orderId;
    private String providerRef;
    private String idempotencyKey;
}