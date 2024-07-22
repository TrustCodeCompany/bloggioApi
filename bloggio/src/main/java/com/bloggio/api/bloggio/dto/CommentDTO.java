package com.bloggio.api.bloggio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO implements Serializable {

    private UUID commentId;

    private Integer commentLikes;

    private String commentContent;

    private Integer commentState;

    private UUID commentIdReply;

    private Date commentTimestampCreate;

    private Date commentTimestampUpdate;

    private UUID postId;

    private UsersDTO usersDTO;

}
