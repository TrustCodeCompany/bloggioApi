package com.bloggio.api.bloggio.persistence.repository;

import com.bloggio.api.bloggio.persistence.entity.Post;
import com.bloggio.api.bloggio.persistence.projection.PostByFilters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    // List<PostByFilters> findTop4ByOrderByPostLikesDesc();

    @Query(value = "select cast(p.post_id as text) as postId, p.post_content as postContent, p.post_description as postDescription, p.likes,\n" +
            "p.post_priority as postPriority, p.post_state as postState, p.post_timestamp_create as postCreated,\n" +
            "p.post_title as postTitle, p.post_image as postImage, p.published, c.category_name as categoryName, cast(u.user_id as text) as userId, u.user_nickname as userNickname \n" +
            "from post p \n" +
            "join category c on c.category_id = p.category_id \n" +
            "join users u on u.user_id = p.user_id \n" +
            "where p.post_state > 0\n" +
            "order by p.likes desc \n" + "limit 4", nativeQuery = true)
    List<PostByFilters> getTop4PostByLikes();

    @Query(value = "select cast(p.post_id as text) as postId, p.post_content as postContent, p.post_description as postDescription, p.likes,\n" +
            "p.post_priority as postPriority, p.post_state as postState, p.post_timestamp_create as postCreated,\n" +
            "p.post_title as postTitle, p.post_image as postImage, p.published, c.category_name as categoryName, cast(u.user_id as text) as userId, u.user_nickname as userNickname,\n" +
            "count(*) OVER() AS fullCount\n" +
            "from post p \n" +
            "join category c on c.category_id = p.category_id \n" +
            "join users u on u.user_id = p.user_id \n" +
            "where p.post_state > 0 \n" +
            "order by p.post_timestamp_create desc \n" +
            "OFFSET (?1-1)*?2 \n" +
            "LIMIT ?2", nativeQuery = true)
    List<PostByFilters> getAllPostByDateAndPage(int offset, int limit);

    @Query(value = "select cast(p.post_id as text) as postId, p.post_content as postContent, p.post_description as postDescription, p.likes,\n" +
            "p.post_priority as postPriority, p.post_state as postState, p.post_timestamp_create as postCreated,\n" +
            "p.post_title as postTitle, p.post_image as postImage, p.published, c.category_name as categoryName, cast(u.user_id as text) as userId, u.user_nickname as userNickname,\n" +
            "count(*) OVER() AS fullCount\n" +
            "from post p \n" +
            "join category c on c.category_id = p.category_id \n" +
            "join users u on u.user_id = p.user_id \n" +
            "where (?3 = '' or c.category_name ilike ?3) and \n" +
            "\t  (?4 = '' or p.post_title ilike ?4) and \n" +
            "\t  ((cast(?5 as date) is null or cast(?6 as date) is null) or cast(p.post_timestamp_create as date) between cast(?5 as date) and cast(?6 as date)) \n" +
            "order by p.likes desc nulls last \n" +
            "OFFSET (?1-1)*?2 \n" +
            "LIMIT ?2", nativeQuery = true)
    List<PostByFilters> getAllPostByFilter(int offset, int limit, String category_name, String post_title,
                                           LocalDate post_creation_start, LocalDate post_creation_end);

    @Query(value = "select cast(p.post_id as text) as postId, p.post_content as postContent, p.post_description as postDescription, p.likes,\n" +
            "p.post_priority as postPriority, p.post_state as postState, p.post_timestamp_create as postCreated,\n" +
            "p.post_title as postTitle, p.post_image as postImage, p.published, c.category_name as categoryName, cast(u.user_id as text) as userId, u.user_nickname as userNickname,\n" +
            "count(*) OVER() AS fullCount\n" +
            "from post p \n" +
            "join category c on c.category_id = p.category_id \n" +
            "join users u on u.user_id = p.user_id \n" +
            "where cast(u.user_id as text) = ?3 and p.post_state > 0\n"+
            "order by p.likes desc nulls last \n" +
            "OFFSET (?1-1)*?2 \n" +
            "LIMIT ?2", nativeQuery = true)
    List<PostByFilters> getPostsByUserId(int offset, int limit, String userId);

    @Modifying
    @Query("update Post p set p.postContent =:postContent, p.postDescription =:postDescription, p.postImage =:postImage, \n" +
            "p.postLikes =:likes, p.postPriority =:priority, p.postState =:state, p.postTimestampUpdate =:timestampUpdate, p.postTitle =:title, \n" +
            "p.published =:published \n" +
            "where p.postId =:postId")
    void updatePost(@Param("postId") UUID postId, @Param("postContent") String postContent,
                    @Param("postDescription") String postDescription, @Param("postImage") String postImage, @Param("likes") Integer likes,
                    @Param("priority") Integer priority, @Param("state") Integer state,
                    @Param("timestampUpdate") Date timestampUpdate, @Param("title") String title, @Param("published") Integer published);

    @Modifying
    @Query("update Post p set p.postState =:state where p.user.userId =:userId")
    void disabledPostByUserId(@Param("userId") UUID userId, @Param("state") Integer state);
}
