package com.stylesphere.service;

import com.stylesphere.model.dto.OrderedProductsResponseDto;
import com.stylesphere.model.dto.ReviewDto;

import java.io.IOException;

public interface ReviewService {
    OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId);

    ReviewDto giveReview(ReviewDto reviewDto) throws IOException;
}
