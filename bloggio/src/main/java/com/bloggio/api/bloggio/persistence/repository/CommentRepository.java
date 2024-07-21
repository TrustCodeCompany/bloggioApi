package com.bloggio.api.bloggio.persistence.repository;

import com.bloggio.api.bloggio.persistence.entity.Comment;
import com.bloggio.api.bloggio.persistence.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    @Query("select c from Comment c where c.post.postId=:postId")
    List<Comment> findAllByPost(@Param("postId") UUID postId);
}
