package com.stylesphere.web;

import com.stylesphere.model.dto.ProductDetailsDto;
import com.stylesphere.model.dto.ProductDto;
import com.stylesphere.service.CustomerProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerProductController {
    private final CustomerProductService productService;

    public CustomerProductController(CustomerProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtos = productService.getAllProducts();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/products/search/{name}")
    public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String name) {
        List<ProductDto> productDtos = productService.searchProductsByTitle(name);
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDetailsDto> getProductDetailsById(@PathVariable Long productId) {
        ProductDetailsDto productDetailsDto = productService.getProductDetailsById(productId);
        if (productDetailsDto == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(productDetailsDto);
    }
}
