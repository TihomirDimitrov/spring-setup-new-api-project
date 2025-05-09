package com.spring.api.example.app.controller;

import com.spring.api.example.app.dto.UserRequestDto;
import com.spring.api.example.app.dto.UserResponseDto;
import com.spring.api.example.app.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    // Add endpoints for user management here
    @PostMapping
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid UserRequestDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.registerNewUser(userDto));    }
}
