package com.bloggio.api.bloggio.dto;

import com.bloggio.api.bloggio.payload.post.FKPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentSaveDTO implements Serializable {

    private UUID commentId;

    private Integer commentLikes;

    private String commentContent;

    private Integer commentState;

    private UUID commentIdReply;

    private Date commentTimestampCreate;

    private Date commentTimestampUpdate;

    @NotNull
    @FKPost
    private UUID postId;

}
