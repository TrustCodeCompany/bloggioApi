package com.bloggio.api.bloggio.dto;

import com.bloggio.api.bloggio.payload.users.ExistUserNickname;
import com.bloggio.api.bloggio.payload.users.UniqueUserNickname;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ExistUserNickname
    @NotNull
    @NotBlank
    @Size(min = 5 , max = 100)
    private String userNickname;

    @Size(max = 200)
    private String userShortBio;

    private Boolean imageOldSelectedName;

}
