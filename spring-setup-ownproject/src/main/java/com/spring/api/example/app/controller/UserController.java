package com.spring.api.example.app.controller;

import com.spring.api.example.app.dto.UserRequestDto;
import com.spring.api.example.app.dto.UserResponseDto;
import com.spring.api.example.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "User management API")
@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Register a new user", description = "Creates a new user and stores it in the database.")
    @ApiResponse(responseCode = "201", description = "User successfully created")
    @ApiResponse(responseCode = "400", description = "Validation failed for user input")
    @ApiResponse(responseCode = "409", description = "Email already exists in the system")
    @PostMapping
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid UserRequestDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.registerNewUser(userDto));
    }

    @Operation(summary = "Get a user by email",
            description = "Retrieves a user from the system using their unique email address.")
    @ApiResponse(responseCode = "200", description = "User found and returned successfully in JSON format")
    @ApiResponse(responseCode = "400", description = "Invalid email format provided")
    @ApiResponse(responseCode = "404", description = "User not found with the given email")
    @GetMapping("/email")
    public ResponseEntity<UserResponseDto> getUserByEmail(@RequestParam @Email String email) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUserByEmail(email));
    }

    @Operation(summary = "Get a user by ID",
            description = "Retrieves a user from the system using their unique id.")
    @ApiResponse(responseCode = "200", description = "User found successfully")
    @ApiResponse(responseCode = "404", description = "User not found with the provided ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(
            @Parameter(description = "Unique ID of the user in our DB", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUserById(id));
    }

    @Operation(summary = "Update an existing user", description = "Updates a user's full name and email by their ID." +
            " Returns 404 if user is not found, 409 if email is taken."
    )
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "409", description = "Email already in use by another user")
    @PutMapping("{id}")
    public ResponseEntity<UserResponseDto> updateUserById(
            @Parameter(description = "ID of the user to be updated", required = true, example = "1")
            @PathVariable Long id,
            @RequestBody @Valid UserRequestDto userRequestDto) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateUserById(id, userRequestDto));
    }

}
