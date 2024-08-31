package com.example.barber_appointment.controller;

import com.example.barber_appointment.business.abstracts.AuthenticationService;
import com.example.barber_appointment.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        authenticationService.loginOrRegister(userDto);
        return ResponseEntity.ok("Verification code sent.");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam String phoneNumber, @RequestParam String code) {
        String token = authenticationService.verifyAndGenerateToken(phoneNumber, code);
        return ResponseEntity.ok(token); // Token'ı yanıt olarak döndür
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam String token, @RequestParam String phoneNumber) {
        boolean isValid = authenticationService.validateToken(token, phoneNumber);
        if (isValid) {
            return ResponseEntity.ok("Token is valid.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token.");
        }
    }
}
