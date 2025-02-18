package com.example.identity_service.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UserUpdateRequest;
import com.example.identity_service.dto.response.UserResponse;
import com.example.identity_service.entity.User;
import com.example.identity_service.enums.Role;
import com.example.identity_service.exception.AppException;
import com.example.identity_service.exception.ErrorCode;
import com.example.identity_service.mapper.UserMapper;
import com.example.identity_service.repository.RoleRepository;
import com.example.identity_service.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTS);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

        //        user.setRoles(roles);
        //        return userRepository.save(user);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse getMyinfo() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        return userMapper.toUserResponse(
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        log.info("In method getUsers");
        return userMapper.toUserResponseList(userRepository.findAll());
    }

    //    @PostAuthorize("returnObject.username == authentication.name")
    @PreAuthorize("#userId == authentication.principal.claims['userId']")
    public UserResponse getUser(String userId) {
        log.info("In method getUser by id");
        return userMapper.toUserResponse(
                userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    @PreAuthorize("#userId == authentication.principal.claims['userId'] or hasRole('ADMIN')")
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        userMapper.updateUser(user, request);

        var roles = roleRepository.findAllById(request.getRoles());
        // check authentication la ADMIN thi moi update role
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            user.setRoles(new HashSet<>(roles));
        }

        //        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.claims['userId']")
    public void deleteUser(String userId) {
        log.info("In method deleteUser by id");
        userRepository.deleteById(userId);
    }
}
