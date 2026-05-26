package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.request.UpdateCategoryRequest;
import co.edu.usbcali.ecommerceusb.dto.response.CategoryResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateCategoryRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteCategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();
    CategoryResponse getCategoryById(Integer id);
    CategoryResponse createCategory(CreateCategoryRequest request);
    CategoryResponse updateCategory(Integer id, UpdateCategoryRequest request);
    DeleteCategoryResponse deleteCategory(Integer id);
}
