package com.bloggio.api.bloggio.payload.post.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreatePostRequest {
    private String post;
    private MultipartFile file;
}
