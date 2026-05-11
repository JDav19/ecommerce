package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.request.UpdateProductRequest;
import co.edu.usbcali.ecommerceusb.dto.response.ProductResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateProductRequest;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Integer id) throws Exception;
    ProductResponse createProduct(CreateProductRequest request);
    ProductResponse updateProduct(Integer id, UpdateProductRequest request) throws Exception;
}
