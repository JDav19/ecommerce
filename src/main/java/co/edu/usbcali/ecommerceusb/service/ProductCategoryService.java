package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.request.CreateProductCategoryRequest;
import co.edu.usbcali.ecommerceusb.dto.response.ProductCategoryResponse;
import java.util.List;

public interface ProductCategoryService {
    List<ProductCategoryResponse> findAll();
    ProductCategoryResponse findById(Integer id);
    ProductCategoryResponse create(CreateProductCategoryRequest request);
}