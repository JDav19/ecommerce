package co.edu.usbcali.ecommerceusb.dto;

import lombok.Data;

@Data
public class CreatePaymentRequest {
    private Integer orderId;
    private String providerRef;
    private String idempotencyKey;
}