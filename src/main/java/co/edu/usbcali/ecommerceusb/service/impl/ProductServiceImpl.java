package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.request.UpdateProductRequest;
import co.edu.usbcali.ecommerceusb.dto.response.ProductResponse;
import co.edu.usbcali.ecommerceusb.dto.request.CreateProductRequest;
import co.edu.usbcali.ecommerceusb.mapper.ProductMapper;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import co.edu.usbcali.ecommerceusb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductResponse> getAllProducts() {
        return ProductMapper.modelToProductResponseList(productRepository.findAll());
    }

    @Override
    public ProductResponse getProductById(Integer id) throws Exception {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception("Producto no encontrado"));
        return ProductMapper.modelToProductResponse(product);
    }

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {
        Product product = ProductMapper.createProductRequestToProduct(request);
        return ProductMapper.modelToProductResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse updateProduct(Integer id, UpdateProductRequest request) throws Exception {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception("El producto a actualizar no existe"));
        if (request.getPrice() != null && request.getPrice().compareTo(java.math.BigDecimal.ZERO) < 0) {
            throw new Exception("El precio no puede ser negativo");
        }
        ProductMapper.updateProductFromRequest(product, request);
        return ProductMapper.modelToProductResponse(productRepository.save(product));
    }
}