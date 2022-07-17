package com.animal.park.service;

import com.animal.park.entity.Role;
import com.animal.park.entity.User;
import com.animal.park.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private static final int ZERO = 0;
    private static int attempts = ZERO;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public boolean saveUser(User user) {
        User byUsername = userRepository.findByUsername(user.getUsername());
        if (byUsername == null) {
            user.setRole(new Role(1, "ROLE_USER"));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean findByUsernameAndPassword(String username, String password) {
        User user = findByUsername(username);
        if (user != null && attempts < 10) {
            attempts = ZERO;
            return passwordEncoder.matches(password, user.getPassword());
        } else {
            attempts++;
            return false;
        }
    }

    @Scheduled(fixedDelay = 3_600_000)
    public void resetAttempts() {
        attempts = ZERO;
    }
}
