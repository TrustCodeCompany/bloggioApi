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

    private String token;
    private String type = "Bearer";
    private UUID userId;
    private String userNickname;
    private String userEmail;
    private String userPhoto;
    private String userShortBio;
    private List<String> roles;

}
