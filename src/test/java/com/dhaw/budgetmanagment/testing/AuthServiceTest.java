package com.dhaw.budgetmanagment.testing;

import com.dhaw.budgetmanagment.models.User;
import com.dhaw.budgetmanagment.repositories.UserRepository;
import com.dhaw.budgetmanagment.services.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    public AuthServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignUp() {
        // Given
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("plaintextpassword");

        // When
        when(passwordEncoder.encode("plaintextpassword")).thenReturn("encodedpassword");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = authService.register(user);

        // Then
        assertNotNull(registeredUser);
        assertEquals("test@example.com", registeredUser.getEmail());
        verify(passwordEncoder).encode("plaintextpassword");
        verify(userRepository).save(registeredUser);
    }
}
