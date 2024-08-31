package com.example.barber_appointment.business.concretes;

import com.example.barber_appointment.business.abstracts.AuthenticationService;
import com.example.barber_appointment.business.abstracts.SmsService;
import com.example.barber_appointment.business.abstracts.UserService;
import com.example.barber_appointment.dto.UserDto;
import com.example.barber_appointment.model.Role;
import com.example.barber_appointment.model.User;
import com.example.barber_appointment.util.JwtUtil;
import com.example.barber_appointment.util.PhoneNumberUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationManager implements AuthenticationService {

    private final UserService userService;
    private final SmsService smsService;
    private final JwtUtil jwtUtil;


    /**
     * Kullanıcının telefon numarası ile giriş yapmasını sağlar.
     * Kullanıcı yoksa, yeni bir kullanıcı oluşturur ve SMS ile doğrulama kodu gönderir.
     */
    @Override
    public void loginOrRegister(UserDto userDto) {
        // Telefon numarasını uluslararası formata çevir
        String formattedPhoneNumber = PhoneNumberUtil.formatPhoneNumber(userDto.getPhoneNumber());

        User user = userService.FindByPhoneNumber(formattedPhoneNumber);

        if (user == null) {
            // Kullanıcı yoksa yeni bir kullanıcı oluştur
            userDto.setPhoneNumber(formattedPhoneNumber); // Kullanıcının numarasını da güncelle
            userService.CreateUser(userDto);
            // user = userService.FindByPhoneNumber(formattedPhoneNumber);
        }

        // Doğrulama SMS'i gönder
        smsService.sendVerificationSms(formattedPhoneNumber);
    }

    /**
     * Kullanıcının gönderdiği doğrulama kodunu kontrol eder.
     * Kod doğruysa JWT token üretir.
     */
    @Override
    public String verifyAndGenerateToken(String phoneNumber, String code) {
        // Telefon numarasını uluslararası formata çevir
        String formattedPhoneNumber = PhoneNumberUtil.formatPhoneNumber(phoneNumber);

        boolean isVerified = smsService.checkVerificationCode(formattedPhoneNumber, code);
        if (isVerified) {
            User user = userService.FindByPhoneNumber(formattedPhoneNumber);
            // JWT token'ı oluştur ve döndür
            return jwtUtil.generateToken(user.getPhoneNumber(), user.getRole());
        } else {
            throw new RuntimeException("Verification failed");
        }
    }

    /**
     * JWT token doğrulaması yapar.
     */
    @Override
    public boolean validateToken(String token, String phoneNumber) {
        return jwtUtil.validateToken(token, phoneNumber);
    }

    /**
     * Token'dan kullanıcının telefon numarasını alır.
     */
    @Override
    public String getPhoneNumberFromToken(String token) {
        return jwtUtil.getPhoneNumberFromToken(token);
    }

    /**
     * Token'dan kullanıcının rolünü alır.
     */
    @Override
    public Role getRoleFromToken(String token) {
        return jwtUtil.getRoleFromToken(token);
    }
}
