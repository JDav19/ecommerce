package co.edu.usbcali.ecommerceusb.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CategoryResponse {
    private Integer id;
    private String name;
    private Integer parentId;
    private String parentName;
}