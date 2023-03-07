package com.bloggio.api.bloggio.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Post")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Post {

        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        private UUID postId;

        @Column(columnDefinition = "TEXT")
        private String postContent;

        @Column()
        private String postTitle;

        @Column()
        private String postDescription;

        @Column(columnDefinition = "integer default 1")
        private Integer postState;

        @Column(columnDefinition = "integer default 1")
        private Integer postPriority;

        @Column(updatable = false)
        @CreationTimestamp
        private Timestamp postTimestampCreate;

        @Column(insertable = false)
        @UpdateTimestamp
        private Timestamp postTimestampUpdate;

        @Column(name = "likes")
        private Integer postLikes;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "userId", nullable = false)
        private Users user;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "categoryId", nullable = false)
        private Category category;

}
