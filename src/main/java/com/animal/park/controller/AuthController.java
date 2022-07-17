package com.animal.park.controller;

import com.animal.park.config.jwt.JwtProvider;
import com.animal.park.entity.User;
import com.animal.park.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {
    private UserService userService;
    private JwtProvider jwtProvider;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registerUser(@RequestBody @Valid User user) {
        boolean saveUser = userService.saveUser(user);
        return saveUser
                ? new ResponseEntity<>("registration completed successfully", (HttpStatus.CREATED))
                : new ResponseEntity<>("user with this name exists", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/login")
    public ResponseEntity<String> auth(@RequestBody User user) {
        String token = jwtProvider.generateToken(user.getUsername());
        boolean user1 = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        return user1
                ? new ResponseEntity<>("successfully received a token: " + token, (HttpStatus.OK))
                : new ResponseEntity<>("Incorrect login or password", HttpStatus.NOT_FOUND);
    }
}
