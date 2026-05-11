package co.edu.usbcali.ecommerceusb.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateInventoryMovementRequest {
    private String type;
    private Integer qty;
}