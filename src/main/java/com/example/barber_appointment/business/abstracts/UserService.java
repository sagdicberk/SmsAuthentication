package com.example.barber_appointment.business.abstracts;

import com.example.barber_appointment.dto.UserDto;
import com.example.barber_appointment.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    void CreateUser(UserDto user);
    void UpdateUser(UserDto user, long id);
    void DeleteUser(long id);
    User FindByPhoneNumber(String phoneNumber);
}
