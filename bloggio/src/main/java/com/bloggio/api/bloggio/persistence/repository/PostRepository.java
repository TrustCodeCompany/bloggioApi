package com.bloggio.api.bloggio.persistence.repository;

import com.bloggio.api.bloggio.persistence.entity.Post;
import com.bloggio.api.bloggio.persistence.projection.PostByFilters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

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
}
