package co.edu.usbcali.ecommerceusb.dto;

import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Builder
@Getter
public class OrderResponse {
    private Integer id;
    private Integer userId;
    private String userName;
    private String status;
    private BigDecimal totalAmount;
    private String currency;
    private OffsetDateTime createdAt;
}