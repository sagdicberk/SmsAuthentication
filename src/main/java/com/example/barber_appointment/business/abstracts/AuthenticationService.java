package com.example.barber_appointment.business.abstracts;

import com.example.barber_appointment.dto.UserDto;
import com.example.barber_appointment.model.Role;

public interface AuthenticationService {
   void loginOrRegister(UserDto userDto);

    String verifyAndGenerateToken(String phoneNumber, String code);

    boolean validateToken(String token, String phoneNumber);

    String getPhoneNumberFromToken(String token);

    Role getRoleFromToken(String token);
}
