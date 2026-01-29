package com.maiboroda.GoGo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.atomic.DoubleAccumulator;

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

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 55)
    private String lastName;

    @Column(nullable = true, length = 55)
    private String middleName;

    @Column(nullable = false, length = 55)
    private LocalDate dateOfBirth;

    @Column(nullable = false, length = 15)
    private String driverLicenseID;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 20)
    private String password;
}
