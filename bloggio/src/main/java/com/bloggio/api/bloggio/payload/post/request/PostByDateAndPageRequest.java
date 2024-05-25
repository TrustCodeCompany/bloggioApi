package com.bloggio.api.bloggio.payload.post.request;

import lombok.Data;

@Data
public class PostByDateAndPageRequest {
    int limit;
    int offset;
}
