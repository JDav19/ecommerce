package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.request.CreateProductCategoryRequest;
import co.edu.usbcali.ecommerceusb.dto.response.ProductCategoryResponse;
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
                .orElseThrow(() -> new RuntimeException("Relación Producto-Categoría no encontrada"));
        return ProductCategoryMapper.modelToResponse(pc);
    }

    @Override
    public ProductCategoryResponse create(CreateProductCategoryRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        ProductCategory productCategory = ProductCategoryMapper.requestToModel(product, category);
        return ProductCategoryMapper.modelToResponse(productCategoryRepository.save(productCategory));
    }
}