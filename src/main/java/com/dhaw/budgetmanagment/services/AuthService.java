package com.dhaw.budgetmanagment.services;

import com.dhaw.budgetmanagment.models.User;
import com.dhaw.budgetmanagment.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String SECRET_KEY = "naruke266";
    private final long EXPIRATION_TIME = 864_000_000; // 10 days

    // Register a new user
    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Login a user and generate a JWT
    public String login(String email, String password, HttpServletResponse response) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate token
        String token = generateToken(user);
        setTokenCookie(token, response); // Set JWT in cookie

        return token;
    }

    // Generate JWT token
    private String generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        Date now = new Date();
        Date validity = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Set JWT token in a cookie
    private void setTokenCookie(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie("JWT", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(86400); // 1 day
        cookie.setPath("/"); // Accessible throughout the application
        response.addCookie(cookie);
    }

    // Logout the user by invalidating the token (optional logic)
    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("JWT", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // Remove cookie
        cookie.setPath("/"); // Accessible throughout the application
        response.addCookie(cookie);
    }
}
