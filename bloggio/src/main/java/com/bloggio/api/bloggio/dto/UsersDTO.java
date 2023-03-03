package com.bloggio.api.bloggio.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bloggio.api.bloggio.payload.users.UniqueUserEmail;
import com.bloggio.api.bloggio.payload.users.UniqueUserNickname;

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

    @NotBlank(message = "Can not be blank")
    @NotNull(message = "Can not be null")
    @Size(min = 15, max = 40, message = "The size should be between 15 to 40")
    @Email(message = "The format must be email type")
    @UniqueUserEmail
    private String userEmail;

    @NotBlank(message = "Can not be blank")
    @NotNull(message = "Can not be null")
    @Size(min = 5, max = 30, message = "The size should be between 5 to 30")
    @UniqueUserNickname
    private String userNickname;

    @NotBlank(message = "Can not be blank")
    @NotNull(message = "Can not be null")
    @Size(min = 5, max = 20, message = "The size should be between 5 to 20")
    private String userPassword;

    private String userPhoto;

    @Size(min = 0, max = 200, message = "The size must not exceed 200 characters")
    private String userShortBio;

    private Integer userState;

}
