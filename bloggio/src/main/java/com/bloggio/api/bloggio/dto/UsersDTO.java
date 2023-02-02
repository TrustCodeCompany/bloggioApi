package com.bloggio.api.bloggio.dto;

import java.io.Serializable;

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

    private String userNickname;

    private String userPassword;

    private String userPhoto;

    private String userShortBio;

}
