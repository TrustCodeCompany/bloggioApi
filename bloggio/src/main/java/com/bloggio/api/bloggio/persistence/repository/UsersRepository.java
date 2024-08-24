package com.bloggio.api.bloggio.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bloggio.api.bloggio.persistence.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {

    Optional<Users> findByUserEmail(String userEmail);

    Optional<Users> findByUserNickname(String userNickname);

    Boolean existsByUserNickname(String userNickname);

    Boolean existsByUserEmail(String userEmail);

    @Modifying
    @Query("update Users u set u.userState =:state where u.userId =:uuid")
    void updateStateUser(UUID uuid, int state);

}
