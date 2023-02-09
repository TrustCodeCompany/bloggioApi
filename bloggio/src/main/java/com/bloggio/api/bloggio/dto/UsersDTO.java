package com.bloggio.api.bloggio.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    @NotBlank
    @NotNull
    @Size(min = 10, max = 60)
    // Validacion unique
    private String userEmail;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 30)
    // Validacion unique
    private String userNickname;

    @NotBlank
    @NotNull
    @Size(min = 5, max = 20)
    private String userPassword;

    private String userPhoto;

    @Size(min = 0, max = 200)
    private String userShortBio;

}
