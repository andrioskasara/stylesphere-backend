package com.stylesphere.model;

import com.stylesphere.model.enumerations.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    public User() {
    }
}
