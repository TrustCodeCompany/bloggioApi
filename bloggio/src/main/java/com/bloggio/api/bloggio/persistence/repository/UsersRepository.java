package com.bloggio.api.bloggio.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bloggio.api.bloggio.persistence.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {

}
