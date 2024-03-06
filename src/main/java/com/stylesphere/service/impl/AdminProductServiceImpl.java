package com.stylesphere.service.impl;

import com.stylesphere.model.Category;
import com.stylesphere.model.Product;
import com.stylesphere.model.dto.ProductDto;
import com.stylesphere.model.exceptions.CategoryNotFoundException;
import com.stylesphere.repository.CategoryRepository;
import com.stylesphere.repository.ProductRepository;
import com.stylesphere.service.AdminProductService;
import org.springframework.stereotype.Service;

import java.util.List;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminProductServiceImpl implements AdminProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public AdminProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public ProductDto getProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.map(Product::getDto).orElse(null);
    }

    public List<ProductDto> getAllProductsByName(String name) {
        List<Product> products = productRepository.findAllByNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public ProductDto createProduct(ProductDto productDto) throws IOException {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImg(productDto.getImg().getBytes());
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException(productDto.getCategoryName()));
        product.setCategory(category);
        return productRepository.save(product).getDto();
    }

    public ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException {
        Optional<Product> productOptional = productRepository.findById(productId);
        Optional<Category> categoryOptional = categoryRepository.findById(productDto.getCategoryId());
        if (productOptional.isPresent() && categoryOptional.isPresent()) {
            Product product = productOptional.get();
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            product.setCategory(categoryOptional.get());
            if (productDto.getImg() != null)
                product.setImg(productDto.getImg().getBytes());
            return productRepository.save(product).getDto();
        } else return null;
    }

    public boolean deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
