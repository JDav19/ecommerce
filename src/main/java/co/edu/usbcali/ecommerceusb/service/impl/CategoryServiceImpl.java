package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.request.UpdateCategoryRequest;
import co.edu.usbcali.ecommerceusb.dto.response.CategoryResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateCategoryRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteCategoryResponse;
import co.edu.usbcali.ecommerceusb.exception.BadRequestException;
import co.edu.usbcali.ecommerceusb.exception.ResourceNotFoundException;
import co.edu.usbcali.ecommerceusb.mapper.CategoryMapper;
import co.edu.usbcali.ecommerceusb.model.Category;
import co.edu.usbcali.ecommerceusb.repository.CategoryRepository;
import co.edu.usbcali.ecommerceusb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> getAllCategories() {
        return CategoryMapper.modelToCategoryResponseList(categoryRepository.findAll());
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        return CategoryMapper.modelToCategoryResponse(category);
    }

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        Category parent = null;

        if (request.getParentId() != null) {
            parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("La categoría padre no existe"));
        }

        Category category = CategoryMapper.createCategoryRequestToCategory(request, parent);
        return CategoryMapper.modelToCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse updateCategory(Integer id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La categoría a actualizar no existe"));

        Category parent = null;
        if (request.getParentId() != null) {
            if (id.equals(request.getParentId())) {
                throw new BadRequestException("Una categoría no puede ser padre de sí misma");
            }
            parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("La categoría padre especificada no existe"));
        }

        CategoryMapper.updateCategoryFromRequest(category, request, parent);

        return CategoryMapper.modelToCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public DeleteCategoryResponse deleteCategory(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Ingrese ID para eliminar");
        }
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("No se encontró la categoría con id %d", id)));
        categoryRepository.delete(category);
        return DeleteCategoryResponse.builder()
                .message(String.format("Categoría con id %d removida correctamente", id))
                .build();
    }
}