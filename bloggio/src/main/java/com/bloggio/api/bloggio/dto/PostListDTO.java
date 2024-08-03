package com.bloggio.api.bloggio.dto;

import com.bloggio.api.bloggio.persistence.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostListDTO {

    private UUID postId;

    private String postContent;

    private String postTitle;

    private String postDescription;

    private Integer postState;

    private Integer postPriority;

    private String postImage;

    private Users user;

    private String categoryDesc;

    private LocalDate postCreated;

}
