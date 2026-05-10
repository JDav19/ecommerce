package co.edu.usbcali.ecommerceusb.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DocumentTypeResponse {
    private Integer id;
    private String code;
    private String name;
}