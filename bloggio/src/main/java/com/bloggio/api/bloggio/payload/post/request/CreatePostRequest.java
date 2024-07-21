package com.bloggio.api.bloggio.payload.post.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class CreatePostRequest implements Serializable {
    private String post;
    private MultipartFile file;
}
