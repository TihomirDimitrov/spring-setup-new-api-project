package com.spring.api.example.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    @Schema(description = "User's full name (required)", example = "Tihomir Dimitrov")
    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;


    @Schema(description = "User email (required)", example = "test@example.com")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email is not valid")
    private String email;

}
