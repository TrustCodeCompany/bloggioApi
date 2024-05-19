package com.bloggio.api.bloggio.persistence.projection;

import java.time.LocalDateTime;

public interface PostByFilters {
    String getPostId();
    String getPostContent();
    String getPostDescription();
    Integer getLikes();
    Integer getPostPriority();
    Integer getPostState();
    LocalDateTime getPostCreated();
    String getPostTitle();
    String getPostImage();
    Integer getPublished();
    String getCategoryName();
    String getUserId();
    String getUserNickname();
    Integer getFullCount();
}
