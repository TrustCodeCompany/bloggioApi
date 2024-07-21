package com.bloggio.api.bloggio.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.bloggio.api.bloggio.payload.users.UniqueUserEmail;
import com.bloggio.api.bloggio.payload.users.UniqueUserNickname;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class SignupRequest {

    @NotBlank
    @Size(min = 5, max = 100)
    @UniqueUserNickname
    private String userNickname;

    @NotBlank
    @Size(max = 100)
    @Email
    @UniqueUserEmail
    private String userEmail;

    private Set<String> roles;

    @NotBlank
    @Size(min = 5, max = 100)
    private String userPassword;

}
