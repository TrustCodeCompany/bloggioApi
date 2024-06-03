package com.bloggio.api.bloggio.payload.post.request;

import lombok.Data;

@Data
public class PostLikeUpdateRequest {
    private int postLikes;
    private String userId;
}
