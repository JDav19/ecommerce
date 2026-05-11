package co.edu.usbcali.ecommerceusb.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateOrderRequest {
    private String status;
    private BigDecimal totalAmount;
    private String currency;
}