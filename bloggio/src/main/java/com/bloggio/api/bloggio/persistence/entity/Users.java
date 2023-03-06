package com.bloggio.api.bloggio.persistence.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "user_email"),
        @UniqueConstraint(columnNames = "user_nickname") })
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

    /*
     * @Column(name = "user_username", length = 40, nullable = false, unique = true)
     * private String userUsername;
     */

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

    public Users(String userEmail, String userNickname, String userPassword) {
        this.userEmail = userEmail;
        this.userNickname = userNickname;
        this.userPassword = userPassword;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

}
