package com.stylesphere.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderedProductsResponseDto {
    private List<ProductDto> productDtos;
    private Double orderAmount;
}
