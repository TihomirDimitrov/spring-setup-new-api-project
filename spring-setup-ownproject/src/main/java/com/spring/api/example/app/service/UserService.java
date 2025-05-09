package com.spring.api.example.app.service;

import com.spring.api.example.app.dto.UserRequestDto;
import com.spring.api.example.app.dto.UserResponseDto;
import com.spring.api.example.app.model.User;
import com.spring.api.example.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * Registers a new user.
     *
     * @param userRequestDto the userRequestDto containing user details
     * @return the registered user
     * @throws IllegalArgumentException if the email already exists
     */
    public UserResponseDto registerNewUser(UserRequestDto userRequestDto) {
        // Check if the user already exists
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException(String.format("Email %s already exists", userRequestDto.getEmail()));
        }
        // Create a new user entity
        User user = User.builder()
                .fullName(userRequestDto.getUsername())
                .email(userRequestDto.getEmail())
                .build();
        User saved = userRepository.save(user);

        return new UserResponseDto(saved.getId(), saved.getFullName(), saved.getEmail());
    }
}
