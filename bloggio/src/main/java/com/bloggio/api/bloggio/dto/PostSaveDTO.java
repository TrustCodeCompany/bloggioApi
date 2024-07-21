package com.bloggio.api.bloggio.dto;

import com.bloggio.api.bloggio.payload.post.FKCategory;
import com.bloggio.api.bloggio.payload.post.FKUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostSaveDTO implements Serializable {

    private UUID postId;

    private String postContent;

    private String postTitle;

    private String postDescription;

    private Integer postState;

    private Integer postPriority;

    private String postImage;

    private Integer published;

    private Integer postLikes;

    @NotNull
    @FKUser
    private UUID userId;

    @NotNull
    @FKCategory
    private UUID categoryId;

}
