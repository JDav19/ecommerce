package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.request.CreatePaymentRequest;
import co.edu.usbcali.ecommerceusb.dto.request.UpdatePaymentRequest;
import co.edu.usbcali.ecommerceusb.dto.response.PaymentResponse;
import java.util.List;

public interface PaymentService {
    List<PaymentResponse> findAll();
    PaymentResponse findById(Integer id);
    PaymentResponse create(CreatePaymentRequest request);
    PaymentResponse update(Integer id, UpdatePaymentRequest request);
}