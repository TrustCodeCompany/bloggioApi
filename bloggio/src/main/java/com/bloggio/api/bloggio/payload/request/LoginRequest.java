package com.bloggio.api.bloggio.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class LoginRequest {

    @NotBlank
    private String usernickname;

    @NotBlank
    private String password;

}
