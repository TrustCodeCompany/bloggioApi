package com.bloggio.api.bloggio.persistence.entity;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Users {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID userId;

    @Column(name = "user_email", length = 40, nullable = false, unique = true)
    private String userEmail;

    @Column(name = "user_nickname", length = 30, nullable = false, unique = true)
    private String userNickname;

    @Column(name = "user_password", length = 20, nullable = false, unique = false)
    private String userPassword;

    @Column(name = "user_photo", length = 20, nullable = true, unique = false)
    private String userPhoto;

    @Column(name = "user_short_bio", length = 200, nullable = true, unique = false)
    private String userShortBio;

    @Column(name = "user_state", nullable = false, columnDefinition = "integer default 1")
    private Integer userState;

    @Column(name = "user_f_create", updatable = false)
    @CreationTimestamp
    private Timestamp userFCreate;

    @Column(name = "user_f_update", insertable = false)
    @UpdateTimestamp
    private Timestamp userFUpdate;

    /*
     * @ManyToMany(fetch = FetchType.LAZY)
     * 
     * @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
     * inverseJoinColumns = @JoinColumn(name = "role_id"))
     * private Set<Role> userRole = new HashSet<>();
     */

}
