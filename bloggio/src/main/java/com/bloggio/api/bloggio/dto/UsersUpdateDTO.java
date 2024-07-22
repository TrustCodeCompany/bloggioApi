package com.bloggio.api.bloggio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersUpdateDTO  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    // TODO validar nombre unico del nickname desde el service - agregar unique key en el campo
    private String userNickname;

    private String userShortBio;

}
