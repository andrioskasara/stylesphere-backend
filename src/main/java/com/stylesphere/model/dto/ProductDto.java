package com.stylesphere.model.dto;

import jakarta.persistence.Lob;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private byte[] byteImage;
    private Long categoryId;
    private String categoryName;
    private MultipartFile image;
    private Long quantity;
}