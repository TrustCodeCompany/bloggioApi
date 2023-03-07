package com.bloggio.api.bloggio.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class SignupRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String userNickname;

    @NotBlank
    @Size(max = 50)
    @Email
    private String userEmail;

    private Set<String> roles;

    @NotBlank
    @Size(min = 6, max = 40)
    private String userPassword;

}
