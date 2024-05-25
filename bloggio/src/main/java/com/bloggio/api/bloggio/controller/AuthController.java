package com.bloggio.api.bloggio.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.bloggio.api.bloggio.dto.UsersUpdateDTO;
import com.bloggio.api.bloggio.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
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

        if (userRepository.existsByUserEmail(signUpRequest.getUserEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User Email is already in use!"));
        }

        if (userRepository.existsByUserNickname(signUpRequest.getUserNickname())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User Nickname is already taken!"));
        }

        // Create new user's account
        Users user = new Users(signUpRequest.getUserEmail(),
                signUpRequest.getUserNickname(),
                encoder.encode(signUpRequest.getUserPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(TRole.T_ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(TRole.T_ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(TRole.T_ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PutMapping("/UpdateById")
    public void UpdateById(@RequestParam UUID userId, @Valid @RequestBody UsersUpdateDTO usersUpdateDTO) {
         authService.updateById(userId, usersUpdateDTO);
    }

}
