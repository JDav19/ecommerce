package co.edu.usbcali.ecommerceusb.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteCartItemResponse {
    private String message;
}