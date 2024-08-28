package com.bloggio.api.bloggio.persistence.repository;

import com.bloggio.api.bloggio.persistence.entity.Comment;
import com.bloggio.api.bloggio.persistence.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    @Query("select c from Comment c where c.post.postId=:postId and c.commentIdReply is null")
    List<Comment> findAllByPost(@Param("postId") UUID postId);


    @Modifying
    @Transactional
    @Query(value = "delete from Comment c where c.post_id =:postId ", nativeQuery = true)
    void deleteAllCommentsByPostId(@Param("postId") UUID postId);

    List<Comment> findAllByCommentIdReply(UUID commentId);
}
