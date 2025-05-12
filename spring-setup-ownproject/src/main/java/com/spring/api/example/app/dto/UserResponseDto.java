package com.spring.api.example.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDto {
    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;

    @Schema(description = "Full name of the user", example = "Tihomir Dimitrov")
    private String fullName;

    @Schema(description = "User's email address", example = "tisho@example.com")
    private String email;
}
