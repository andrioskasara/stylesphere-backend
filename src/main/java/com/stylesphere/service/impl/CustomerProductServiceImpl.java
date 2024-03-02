package com.stylesphere.service.impl;

import com.stylesphere.model.Product;
import com.stylesphere.model.Review;
import com.stylesphere.model.dto.ProductDetailsDto;
import com.stylesphere.model.dto.ProductDto;
import com.stylesphere.repository.ProductRepository;
import com.stylesphere.repository.ReviewRepository;
import com.stylesphere.service.CustomerProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerProductServiceImpl implements CustomerProductService {
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public CustomerProductServiceImpl(ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public List<ProductDto> searchProductsByTitle(String name) {
        List<Product> products = productRepository.findAllByNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public ProductDetailsDto getProductDetailsById(Long productId) {
        Optional<Product>  productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
             List<Review> reviewList = reviewRepository.findAllByProductId(productId);
            ProductDetailsDto productDetailsDto = new ProductDetailsDto();
            productDetailsDto.setProductDto(productOptional.get().getDto());
            productDetailsDto.setReviewDtoList(reviewList.stream().map(Review::getDto).collect(Collectors.toList()));
            return productDetailsDto;
        }
        return null;
    }
}
