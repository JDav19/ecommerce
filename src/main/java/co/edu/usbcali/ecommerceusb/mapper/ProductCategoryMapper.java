package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.response.ProductCategoryResponse;
import co.edu.usbcali.ecommerceusb.model.Category;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.model.ProductCategory;

public class ProductCategoryMapper {

    public static ProductCategory requestToModel(Product product, Category category) {
        return ProductCategory.builder()
                .product(product)
                .category(category)
                .build();
    }

    public static ProductCategoryResponse modelToResponse(ProductCategory model) {
        return ProductCategoryResponse.builder()
                .id(model.getId())
                .productId(model.getProduct().getId())
                .productName(model.getProduct().getName())
                .categoryId(model.getCategory().getId())
                .categoryName(model.getCategory().getName())
                .build();
    }
}