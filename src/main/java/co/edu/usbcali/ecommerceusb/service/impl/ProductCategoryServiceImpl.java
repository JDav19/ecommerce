package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.request.CreateProductCategoryRequest;
import co.edu.usbcali.ecommerceusb.dto.request.UpdateProductCategoryRequest;
import co.edu.usbcali.ecommerceusb.dto.response.DeleteProductCategoryResponse;
import co.edu.usbcali.ecommerceusb.dto.response.ProductCategoryResponse;
import co.edu.usbcali.ecommerceusb.exception.BadRequestException;
import co.edu.usbcali.ecommerceusb.exception.ResourceNotFoundException;
import co.edu.usbcali.ecommerceusb.mapper.ProductCategoryMapper;
import co.edu.usbcali.ecommerceusb.model.Category;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.model.ProductCategory;
import co.edu.usbcali.ecommerceusb.repository.CategoryRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductCategoryRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import co.edu.usbcali.ecommerceusb.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<ProductCategoryResponse> findAll() {
        return productCategoryRepository.findAll().stream()
                .map(ProductCategoryMapper::modelToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductCategoryResponse findById(Integer id) {
        ProductCategory pc = productCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Relación Producto-Categoría no encontrada"));
        return ProductCategoryMapper.modelToResponse(pc);
    }

    @Override
    public ProductCategoryResponse create(CreateProductCategoryRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        ProductCategory productCategory = ProductCategoryMapper.requestToModel(product, category);
        return ProductCategoryMapper.modelToResponse(productCategoryRepository.save(productCategory));
    }

    @Override
    public ProductCategoryResponse update(Integer id, UpdateProductCategoryRequest request) {
        ProductCategory pc = productCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Relación Producto-Categoría no encontrada"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        ProductCategoryMapper.updateModelFromRequest(pc, product, category);
        return ProductCategoryMapper.modelToResponse(productCategoryRepository.save(pc));
    }

    @Override
    public DeleteProductCategoryResponse deleteProductCategory(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Ingrese ID para eliminar");
        }
        ProductCategory productCategory = productCategoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("No se encontró el ProductCategory con id %d", id)));
        productCategoryRepository.delete(productCategory);
        return DeleteProductCategoryResponse.builder()
                .message(String.format("ProductCategory con id %d removido con éxito", id))
                .build();
    }
}