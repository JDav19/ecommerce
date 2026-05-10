package co.edu.usbcali.ecommerceusb.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateOrderItemRequest {
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private BigDecimal unitPriceSnapshot;
}