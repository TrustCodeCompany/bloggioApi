package com.bloggio.api.bloggio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private UUID postId;

    private String postContent;

    private String postTitle;

    private String postDescription;

    private Integer postState;

    private Integer postPriority;

    private UsersDTO users;

}
