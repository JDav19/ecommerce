package co.edu.usbcali.ecommerceusb.dto.request;

import lombok.Data;

@Data
public class CreateProductCategoryRequest {
    private Integer productId;
    private Integer categoryId;
}