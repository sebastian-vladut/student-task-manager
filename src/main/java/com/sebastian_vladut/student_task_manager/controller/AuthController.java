package com.sebastian_vladut.student_task_manager.controller;

import com.sebastian_vladut.student_task_manager.entity.User;
import com.sebastian_vladut.student_task_manager.repository.UserRepository;
import com.sebastian_vladut.student_task_manager.service.JwtService;
import com.sebastian_vladut.student_task_manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // POST /auth/register
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User newUser = userService.registerUser(user);

        return new  ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // POST /auth/login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        String role = authentication.getAuthorities().iterator().next().getAuthority();

        String jwtToken = jwtService.generateToken(user.getUsername(),  role);

        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }
}
