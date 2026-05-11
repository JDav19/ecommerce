package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.request.UpdateCategoryRequest;
import co.edu.usbcali.ecommerceusb.dto.response.CategoryResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateCategoryRequest;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();
    CategoryResponse getCategoryById(Integer id) throws Exception;
    CategoryResponse createCategory(CreateCategoryRequest request) throws Exception;
    CategoryResponse updateCategory(Integer id, UpdateCategoryRequest request) throws Exception;
}
