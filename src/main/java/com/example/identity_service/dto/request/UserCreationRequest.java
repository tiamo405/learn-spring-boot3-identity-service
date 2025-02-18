package com.example.identity_service.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

import com.example.identity_service.validator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3, message = "INVALID_USERNAME")
    String username;

    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;

    String firstName;
    String lastName;

    @DobConstraint(min = 16, message = "INVALID_DOB")
    LocalDate dob;
}
