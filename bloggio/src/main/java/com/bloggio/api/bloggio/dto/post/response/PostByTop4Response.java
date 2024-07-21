package com.bloggio.api.bloggio.dto.post.response;

import com.bloggio.api.bloggio.persistence.projection.PostByFilters;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostByTop4Response {
    private List<PostByFilters> data;
}
