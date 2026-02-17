package com.maiboroda.GoGo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewResponseDTO {

    private String reviewText;
    private String firstName;
    private LocalDate dateOfBirth;
    private String town;

}