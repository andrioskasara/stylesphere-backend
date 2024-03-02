package com.stylesphere.service;

import com.stylesphere.model.dto.ProductDetailsDto;
import com.stylesphere.model.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {
    List<ProductDto> getAllProducts();

    List<ProductDto> searchProductsByTitle(String title);
    ProductDetailsDto getProductDetailsById(Long productId);
}
