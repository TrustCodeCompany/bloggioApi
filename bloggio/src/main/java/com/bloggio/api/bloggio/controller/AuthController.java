package com.bloggio.api.bloggio.controller;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.bloggio.api.bloggio.dto.UsersUpdateDTO;
import com.bloggio.api.bloggio.dto.user.response.ProfileResponse;
import com.bloggio.api.bloggio.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.bloggio.api.bloggio.payload.request.LoginRequest;
import com.bloggio.api.bloggio.payload.request.SignupRequest;
import com.bloggio.api.bloggio.payload.response.JwtResponse;
import com.bloggio.api.bloggio.payload.response.MessageResponse;
import com.bloggio.api.bloggio.persistence.entity.Role;
import com.bloggio.api.bloggio.persistence.entity.TRole;
import com.bloggio.api.bloggio.persistence.entity.Users;
import com.bloggio.api.bloggio.persistence.repository.RoleRepository;
import com.bloggio.api.bloggio.persistence.repository.UsersRepository;
import com.bloggio.api.bloggio.security.jwt.JwtUtils;
import com.bloggio.api.bloggio.security.services.UserDetailsImpl;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Log4j2
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsersRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    ObjectMapper objectMapper;

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserNickname(),
                        loginRequest.getUserPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(new JwtResponse(jwt, "Bearer", userDetails.getUserId(), userDetails.getUserNickname(),
                        userDetails.getUserEmail(), userDetails.getUserPhoto(), userDetails.getUserShortBio(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        // Create new user's account
        Users user = new Users(signUpRequest.getUserEmail(),
                signUpRequest.getUserNickname(),
                encoder.encode(signUpRequest.getUserPassword()));

        // Define Rol Object (Collection)
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();


        // Validate and Assign Rol
        for (String role : strRoles) {
            switch (role) {
                case "ROLE_ADMIN":
                    Role adminRoleCMS = roleRepository.findByName(TRole.ROLE_ADMIN)
                            .orElse(null);
                    if (adminRoleCMS == null) {
                        return ResponseEntity
                                .badRequest()
                                .body(new MessageResponse("Error: Role admin is not found."));
                    }
                    roles.add(adminRoleCMS);
                    break;
                case "ROLE_USER":
                    Role userRoleCMS = roleRepository.findByName(TRole.ROLE_USER)
                            .orElse(null);
                    if (userRoleCMS == null) {
                        return ResponseEntity
                                .badRequest()
                                .body(new MessageResponse("Error: Role user is not found."));
                    }
                    roles.add(userRoleCMS);
                    break;
                case "":
                    Role NullRole = roleRepository.findByName(TRole.ROLE_USER)
                            .orElse(null);
                    if (NullRole == null) {
                        return ResponseEntity
                                .badRequest()
                                .body(new MessageResponse("Error: Role user is not found."));
                    }
                    roles.add(NullRole);
                    break;
                default:
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Error: Invalid role provided."));
            }
        }

        // Save Rol and All Data
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping(value = "/update-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProfileResponse> UpdateById(
            @RequestPart("user") UsersUpdateDTO usersUpdateDTO,
            @RequestPart("file") MultipartFile file) {
        authService.updateById(usersUpdateDTO, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProfileResponse.builder().postId(UUID.randomUUID().toString()).message("ok").build());
    }

    @PostMapping("/reset-request")
    public ResponseEntity<?> requestPasswordReset(@RequestParam String email) {
        try {
            authService.requestPasswordReset(email);
            return ResponseEntity.ok("Password reset link sent");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error sending email");
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        authService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password has been reset");
    }

    /*@PostMapping("/security-request-mail")
    public ResponseEntity<?> securityRequestMail(@RequestParam String email) {
        authService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password has been reset");
    }


    @PostMapping("/disabled-account")
    public ResponseEntity<?> disabledAccount(@RequestParam String token, @RequestParam String newPassword) {
        authService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password has been reset");
    }

    @PostMapping("/enabled-account")
    public ResponseEntity<?> enabledAccount(@RequestParam String token, @RequestParam String newPassword) {
        authService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password has been reset");
    }*/

}



