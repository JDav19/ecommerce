package co.edu.usbcali.ecommerceusb.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCategoryResponse {
    private Integer id;
    private Integer productId;
    private String productName;
    private Integer categoryId;
    private String categoryName;
}