package co.edu.usbcali.ecommerceusb.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.OffsetDateTime;

@Builder
@Getter
public class InventoryMovementResponse {
    private Integer id;
    private Integer productId;
    private String productName;
    private Integer orderId;
    private String type;
    private Integer qty;
    private OffsetDateTime createdAt;
}