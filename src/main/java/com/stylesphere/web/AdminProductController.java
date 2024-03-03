package com.stylesphere.web;

import com.stylesphere.model.dto.ProductDto;
import com.stylesphere.service.AdminProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminProductController {
    private final AdminProductService productService;

    public AdminProductController(AdminProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtos = productService.getAllProducts();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) {
        ProductDto productDto = productService.getProductById(productId);
        if (productDto != null)
            return ResponseEntity.ok(productDto);
        else return ResponseEntity.notFound().build();
    }

    @GetMapping("/products/search/{name}")
    public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String name) {
        List<ProductDto> productDtos = productService.getAllProductsByName(name);
        return ResponseEntity.ok(productDtos);
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDto> createProduct(@ModelAttribute ProductDto productDto) throws IOException {
        ProductDto product = productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/product/edit/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId, @ModelAttribute ProductDto productDto) throws IOException {
        ProductDto updatedProduct = productService.updateProduct(productId, productDto);
        if (updatedProduct != null)
            return ResponseEntity.ok(updatedProduct);
        else return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean deleted = productService.deleteProduct(productId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
