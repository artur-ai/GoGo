package com.maiboroda.GoGo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name can not be empty")
    @Size(min = 2, max = 155, message = "Name must contains from 2 to 20 characters")
    @Pattern(regexp = "^[A-Za-zА-Яа-яЄєІіЇї'\\s-]*$", message = "Name contains invalid characters")
    @Column(nullable = false, length = 155)
    private String name;

    @NotBlank(message = "Email can not be empty")
    @Email(message = "Please enter a valid email address.")
    @Size(max = 120, message = "Email size must be lower 120 characters")
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @NotBlank(message = "Phone can not be empty")
    @Pattern(regexp = "^\\+?[0-9\\s()-]{7,20}$", message = "Please enter a valid phone number.")
    @Column(nullable = false, length = 20)
    private String phone;

    @NotBlank(message = "Password can not be empty")
    @Size(min = 8, max = 255, message = "Password must contains minimum 8 characters")
    @Column(nullable = false, length = 255)
    private String password;

    @NotBlank(message = "Pasport can not be empty")
    @URL(message = "URL must be valid")
    @Column(nullable = false, length = 255, name = "passport_url")
    private String passportUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
