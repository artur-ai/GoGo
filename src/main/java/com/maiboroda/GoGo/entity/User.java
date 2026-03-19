package com.maiboroda.GoGo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 55, name = "first_name")
    private String firstName;

    @Column(nullable = false, length = 55, name = "last_name")
    private String lastName;

    @Column(nullable = true, length = 55, name = "middle_name")
    private String middleName;

    @Column(nullable = false, length = 55, name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(nullable = false, length = 15, name = "driver_license_id")
    private String driverLicenseId;

    @Column(nullable = false,unique = true, length = 254)
    private String email;

    @Column(nullable = false, length = 20)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Address> addressList = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
