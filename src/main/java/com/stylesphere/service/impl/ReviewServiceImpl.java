package com.stylesphere.service.impl;

import com.stylesphere.model.*;
import com.stylesphere.model.dto.OrderedProductsResponseDto;
import com.stylesphere.model.dto.ProductDto;
import com.stylesphere.model.dto.ReviewDto;
import com.stylesphere.repository.OrderRepository;
import com.stylesphere.repository.ProductRepository;
import com.stylesphere.repository.ReviewRepository;
import com.stylesphere.repository.UserRepository;
import com.stylesphere.service.ReviewService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    public OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        OrderedProductsResponseDto orderedProductsResponseDto = new OrderedProductsResponseDto();
        if (orderOptional.isPresent()) {
            orderedProductsResponseDto.setOrderAmount(orderOptional.get().getAmount());
            List<ProductDto> productDtoList = new ArrayList<>();
            for (CartItem items : orderOptional.get().getCartItems()) {
                ProductDto productDto = new ProductDto();
                productDto.setId(items.getProduct().getId());
                productDto.setName(items.getProduct().getName());
                productDto.setPrice(items.getPrice());
                productDto.setQuantity(items.getQuantity());
                productDto.setByteImg(items.getProduct().getImg());
                productDtoList.add(productDto);
            }
            orderedProductsResponseDto.setProductDtoList(productDtoList);
        }
        return orderedProductsResponseDto;
    }

    public ReviewDto giveReview(ReviewDto reviewDto) throws IOException {
        Optional<Product> productOptional = productRepository.findById(reviewDto.getProductId());
        Optional<User> userOptional = userRepository.findById(reviewDto.getUserId());
        if (productOptional.isPresent() && userOptional.isPresent()) {
            Review review = new Review();
            review.setRating(reviewDto.getRating());
            review.setDescription(reviewDto.getDescription());
            review.setUser(userOptional.get());
            review.setProduct(productOptional.get());
            review.setImg(reviewDto.getImg().getBytes());
            return reviewRepository.save(review).getDto();
        }
        return null;
    }
}
