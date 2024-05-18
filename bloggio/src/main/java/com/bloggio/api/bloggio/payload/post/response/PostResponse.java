package com.bloggio.api.bloggio.payload.post.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostResponse {
    String postId;
    String message;
}
