package com.bloggio.api.bloggio.payload.response;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class JwtResponse {

    /*
     * private String token;
     * private String type = "Bearer";
     * private Long id;
     * private String usernickname;
     * private String email;
     * private List<String> roles;
     */

    private String token;
    private String type = "Bearer";
    private UUID userId;
    private String userNickname;
    private String userEmail;
    private List<String> roles;
}
