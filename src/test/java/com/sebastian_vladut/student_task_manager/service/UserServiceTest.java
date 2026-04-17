package com.sebastian_vladut.student_task_manager.service;

import com.sebastian_vladut.student_task_manager.entity.User;
import com.sebastian_vladut.student_task_manager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUser() {
        User user = new User();
        user.setUsername("mock_student");
        user.setPassword("mock_password");

        User savedUser = new User();
        savedUser.setUsername("mock_student");
        savedUser.setPassword("encrypted_mock_password");
        savedUser.setRole("ROLE_USER");

        when(passwordEncoder.encode("mock_password")).thenReturn("encrypted_mock_password");
        when(userRepository.save(user)).thenReturn(savedUser);

        User result = userService.registerUser(user);

        assertNotNull(result);
        assertEquals("encrypted_mock_password", result.getPassword());
        assertEquals("ROLE_USER", result.getRole());
        verify(userRepository, times(1)).save(user);
    }

}
