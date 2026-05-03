package co.edu.usbcali.ecommerceusb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateInventoryRequest {
    private Integer productId;
    private Integer stock;
}