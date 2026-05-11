package co.edu.usbcali.ecommerceusb.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateProductCategoryRequest {
    private Integer productId;
    private Integer categoryId;
}