package com.bloggio.api.bloggio.dto;

import com.bloggio.api.bloggio.payload.users.UniqueUserEmail;
import com.bloggio.api.bloggio.payload.users.UniqueUserNickname;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersUpdateDTO  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    @NotBlank(message = "Can not be blank")
    @NotNull(message = "Can not be null")
    @Size(min = 5, max = 30, message = "The size should be between 5 to 30")
    @UniqueUserNickname
    private String userNickname;

    private String userPhoto;

    @Size(min = 0, max = 200, message = "The size must not exceed 200 characters")
    private String userShortBio;

}
