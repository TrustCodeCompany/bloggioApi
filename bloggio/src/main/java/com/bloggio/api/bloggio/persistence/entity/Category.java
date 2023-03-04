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
@Table(name = "category")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Category {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID categoryId;

    @Column(name = "category_desc", length = 30, nullable = false, unique = false)
    private String categoryDesc;

    @Column(name = "category_state", nullable = false, columnDefinition = "integer default 1")
    private Integer categoryState;

    @Column(name = "category_f_create", updatable = false)
    @CreationTimestamp
    private Timestamp categoryFCreate;

    @Column(name = "category_f_update", insertable = false)
    @UpdateTimestamp
    private Timestamp categoryFUpdate;

}
