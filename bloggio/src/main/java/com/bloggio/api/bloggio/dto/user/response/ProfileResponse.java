package com.bloggio.api.bloggio.dto.user.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponse {
    String postId;
    String message;
}
