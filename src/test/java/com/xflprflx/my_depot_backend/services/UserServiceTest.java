package com.xflprflx.my_depot_backend.services;

import com.xflprflx.my_depot_backend.model.User;
import com.xflprflx.my_depot_backend.model.dtos.request.NewUserRequest;
import com.xflprflx.my_depot_backend.model.dtos.response.NewUserResponse;
import com.xflprflx.my_depot_backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private NewUserRequest newUserRequest;

    @BeforeEach
    void setUp() {
        newUserRequest = new NewUserRequest("Test User", "test@example.com", "password", 1L, 1L);
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
    }

    @Test
    @DisplayName("loadUserByUsername should return User when user exists")
    void loadUserByUsername_shouldReturnUser_whenUserExists() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        User result = userService.loadUserByUsername("test@example.com");

        assertNotNull(result);
        assertEquals("test@example.com", result.getUsername());
    }

    @Test
    @DisplayName("loadUserByUsername should throw UsernameNotFoundException when user does not exist")
    void loadUserByUsername_shouldThrowException_whenUserDoesNotExist() {
        String nonExistentEmail = "nonexistent@example.com";
        when(userRepository.findByEmail(nonExistentEmail)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(nonExistentEmail);
        });
    }

    @Test
    @DisplayName("createNewUser should return NewUserResponse on successful creation")
    void createNewUser_shouldReturnNewUserResponse() {
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User userToSave = invocation.getArgument(0);
            userToSave.setId(1L); // Simulate saving and getting an ID
            return userToSave;
        });

        NewUserResponse result = userService.createNewUser(newUserRequest);

        assertNotNull(result);
        assertEquals("Test User", result.name());
        assertEquals("test@example.com", result.email());
        assertNotNull(result.id());
    }

    @Test
    @DisplayName("loadUserByUsername should throw exception with correct message when user does not exist")
    void loadUserByUsername_shouldThrowException_withCorrectMessage() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("nonexistent@example.com"));
        assertEquals("User not found", exception.getMessage());
    }
}
