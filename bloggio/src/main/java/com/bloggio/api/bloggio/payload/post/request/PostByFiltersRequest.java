package com.bloggio.api.bloggio.payload.post.request;

import lombok.Data;

@Data
public class PostByFiltersRequest {
    int limit;
    int offset;
    String categoryName;
    String postTitle;
    String date_start;
    String date_end;
}
