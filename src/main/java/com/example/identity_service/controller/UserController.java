package com.example.identity_service.controller;

import com.example.identity_service.dto.request.ApiResponse;
import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UserUpdateRequest;
import com.example.identity_service.dto.response.UserResponse;
import com.example.identity_service.entity.User;
import com.example.identity_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<User>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("User: {}", authentication.getName());
        authentication.getAuthorities().forEach(a -> log.info("Role: {}", a.getAuthority()));


//        ApiResponse<List<User>> apiResponse = new ApiResponse<>();
//        apiResponse.setResult(userService.getUsers());
        return ApiResponse.<List<User>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable String userId) {
//        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
//        apiResponse.setResult(userService.getUser(userId));
//        return apiResponse;
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        UserResponse user = userService.getUser(userId);
        if (!authentication.getName().equals(user.getUsername())) {
            throw new RuntimeException("Unauthorized");
        }
        return ApiResponse.<UserResponse>builder()
                .result(user)
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
//        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
//        apiResponse.setResult(userService.updateUser(userId, request));
//        return apiResponse;
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
//        ApiResponse<String> apiResponse = new ApiResponse<>();
//        apiResponse.setResult("User deleted successfully");
//        return apiResponse;
        return ApiResponse.<String>builder()
                .result("User deleted successfully")
                .build();
    }

}
