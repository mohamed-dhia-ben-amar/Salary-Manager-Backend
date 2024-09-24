package com.dhaw.budgetmanagment.testing;

import com.dhaw.budgetmanagment.controllers.AuthController;
import com.dhaw.budgetmanagment.models.User;
import com.dhaw.budgetmanagment.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(authService.register(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = authController.register(user);
        assertEquals("test@example.com", response.getBody().getEmail());
    }

    @Test
    public void testUpdateProfile() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setOccupation("Engineer");
        user.setAnnualSalary(100000);
        user.setMonthlyExpenses(2000);
        user.setOtherRevenues(5000);

        when(authService.updateProfile(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = authController.updateProfile(user);
        assertEquals("Engineer", response.getBody().getOccupation());
        assertEquals(100000, response.getBody().getAnnualSalary());
        assertEquals(2000, response.getBody().getMonthlyExpenses());
        assertEquals(5000, response.getBody().getOtherRevenues());
    }

    @Test
    public void testUpdateWalletBalance() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setWalletBalance(1500);

        when(authService.updateWalletBalance("test@example.com", 1500)).thenReturn(user);

        ResponseEntity<User> response = authController.updateWalletBalance("test@example.com", 1500);
        assertEquals(1500, response.getBody().getWalletBalance());
    }
}