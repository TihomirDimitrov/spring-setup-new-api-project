package com.spring.api.example.app.service;

import com.spring.api.example.app.dto.UserRequestDto;
import com.spring.api.example.app.dto.UserResponseDto;
import com.spring.api.example.app.mapper.UserMapper;
import com.spring.api.example.app.model.User;
import com.spring.api.example.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service layer responsible for user management logic.
 * Handles user creation, retrieval, and update operations.
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Registers a new user after validating the email uniqueness.
     *
     * @param userRequestDto the user data received from the client
     * @return the newly created user response DTO
     * @throws ResponseStatusException if the email already exists
     */
    public UserResponseDto registerNewUser(UserRequestDto userRequestDto) {
        validateEmailNotUsed(userRequestDto.getEmail());

        User user = userMapper.toEntity(userRequestDto);
        userRepository.save(user);
        return userMapper.toUserResponseDto(user);
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email the user's email
     * @return the user response DTO
     * @throws ResponseStatusException if no user is found with the given email
     */
    public UserResponseDto getUserByEmail(String email) {
        User user = findUserByEmail(email);
        return userMapper.toUserResponseDto(user);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the user's ID
     * @return the user response DTO
     * @throws ResponseStatusException if no user is found with the given ID
     */
    public UserResponseDto getUserById(Long id) {
        User user = findUserById(id);
        return userMapper.toUserResponseDto(user);
    }

    /**
     * Updates an existing user's information by ID.
     * Validates that the email is not used by another user.
     *
     * @param id the ID of the user to update
     * @param userRequestDto the new user data
     * @return the updated user response DTO
     * @throws ResponseStatusException if user is not found or email is taken
     */
    public UserResponseDto updateUserById(Long id, UserRequestDto userRequestDto) {
        User existingUser = findUserById(id);

        validateEmailNotUsedByOtherUser(id, userRequestDto.getEmail());

        userMapper.updateUserFromDto(userRequestDto, existingUser);
        existingUser.setEmail(userRequestDto.getEmail());

        return userMapper.toUserResponseDto(userRepository.save(existingUser));
    }

    /**
     * Finds a user by their ID or throws a 404 exception.
     *
     * @param id the user's ID
     * @return the user entity
     */
    private User findUserById(Long id) {
        return userRepository.getUserById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User with id %s not found", id)));
    }

    /**
     * Finds a user by their email or throws a 404 exception.
     *
     * @param email the user's email
     * @return the user entity
     */
    private User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User with email %s not found", email)));
    }

    /**
     * Validates that no user with the given email already exists.
     *
     * @param email the email to check
     * @throws ResponseStatusException if email is already taken
     */
    private void validateEmailNotUsed(String email) {
        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Email %s already exists", email));
        }
    }

    /**
     * Validates that the given email is not used by another user (i.e. different ID).
     *
     * @param userId the ID of the current user being updated
     * @param email the new email to validate
     * @throws ResponseStatusException if the email is used by another user
     */
    private void validateEmailNotUsedByOtherUser(Long userId, String email) {
        userRepository.findUserByEmail(email).ifPresent(user -> {
            if (!user.getId().equals(userId)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        String.format("Email %s is already used by another user", email));
            }
        });
    }
}
