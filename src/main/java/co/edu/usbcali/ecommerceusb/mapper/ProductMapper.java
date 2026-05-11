package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.request.UpdateProductRequest;
import co.edu.usbcali.ecommerceusb.dto.response.ProductResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateProductRequest;
import co.edu.usbcali.ecommerceusb.model.Product;

import java.time.OffsetDateTime;
import java.util.List;

public class ProductMapper {

    public static ProductResponse modelToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .available(product.getAvailable())
                .build();
    }

    public static List<ProductResponse> modelToProductResponseList(List<Product> products) {
        return products.stream().map(ProductMapper::modelToProductResponse).toList();
    }

    public static Product createProductRequestToProduct(CreateProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .available(request.getAvailable() != null ? request.getAvailable() : true)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();
    }

    public static void updateProductFromRequest(Product product, UpdateProductRequest request) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setAvailable(request.getAvailable());
        product.setUpdatedAt(OffsetDateTime.now());
    }
}