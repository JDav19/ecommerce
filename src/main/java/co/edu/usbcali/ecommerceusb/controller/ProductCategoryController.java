package co.edu.usbcali.ecommerceusb.controller;

import co.edu.usbcali.ecommerceusb.dto.request.CreateProductCategoryRequest;
import co.edu.usbcali.ecommerceusb.dto.response.ProductCategoryResponse;
import co.edu.usbcali.ecommerceusb.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-category")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductCategoryResponse>> getAll() {
        return ResponseEntity.ok(productCategoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(productCategoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductCategoryResponse> create(@RequestBody CreateProductCategoryRequest request) {
        return new ResponseEntity<>(productCategoryService.create(request), HttpStatus.CREATED);
    }
}