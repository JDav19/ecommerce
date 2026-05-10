package co.edu.usbcali.ecommerceusb.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean available;
}