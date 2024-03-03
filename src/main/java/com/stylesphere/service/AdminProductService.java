package com.stylesphere.service;

import com.stylesphere.model.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface AdminProductService {
    List<ProductDto> getAllProducts();

    ProductDto getProductById(Long productId);

    List<ProductDto> getAllProductsByName(String name);

    ProductDto createProduct(ProductDto productDto) throws IOException;

    ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException;

    boolean deleteProduct(Long id);
}
