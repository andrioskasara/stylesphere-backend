package com.stylesphere.model.dto;

import com.stylesphere.model.enumerations.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private UserRole userRole;
}
