package com.example.barber_appointment.business.concretes;

import com.example.barber_appointment.business.abstracts.UserService;
import com.example.barber_appointment.dto.UserDto;
import com.example.barber_appointment.model.Role;
import com.example.barber_appointment.model.User;
import com.example.barber_appointment.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserManager implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void CreateUser(UserDto user) {
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setRole(Role.CUSTOMER);
        userRepository.save(newUser);
    }

    @Override
    public void UpdateUser(UserDto user, long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with ID " + id + " not found"));
        existingUser.setName(user.getName());

        userRepository.save(existingUser);

    }

    @Override
    public void DeleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public User FindByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }
}
