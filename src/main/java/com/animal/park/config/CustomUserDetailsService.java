package com.animal.park.config;

import com.animal.park.entity.User;
import com.animal.park.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private UserService userService;
    private User user;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public User getUser() {
        return user;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userService.findByUsername(username);
        user = userEntity;
        return CustomUserDetails.fromUserToCustomUserDetails(userEntity);
    }
}
