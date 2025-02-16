package com.example.identity_service.controller;

import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.response.UserResponse;
import com.example.identity_service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.time.LocalDate;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserControllertest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserService userService;

    private UserCreationRequest userCreationRequest;
    private UserResponse userResponse;
    private LocalDate dob;

    @BeforeEach //
    void initData(){
        dob = LocalDate.of(2000, 1, 1);


        userCreationRequest = UserCreationRequest.builder()
                .username("test12")
                .password("123456789")
                .firstName("test")
                .lastName("test")
                .dob(dob)
                .build();

        userResponse = UserResponse.builder()
                .id("8648648648sdf64")
                .username("test12")
                .firstName("test")
                .lastName("test")
                .dob(dob)
                .build();
    }

    @Test
    void createUser() {
    log.info("UserCreationRequest: {}", userCreationRequest);
    }
}
