package com.bloggio.api.bloggio.service;

import com.bloggio.api.bloggio.dto.UsersUpdateDTO;
import com.bloggio.api.bloggio.exception.Exception;
import com.bloggio.api.bloggio.mapper.UsersMapperImpl;
import com.bloggio.api.bloggio.persistence.entity.Users;
import com.bloggio.api.bloggio.persistence.repository.UsersRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class AuthService {

    private final UsersRepository usersRepository;

    private UsersMapperImpl usersMapperImpl;

    public AuthService (UsersRepository usersRepository , UsersMapperImpl usersMapperImpl) {
        this.usersRepository = usersRepository;
        this.usersMapperImpl = usersMapperImpl;
    }

    public void updateById(UUID userId , UsersUpdateDTO usersUpdateDTO) {
        Optional<Users> findUsersById = usersRepository.findById(userId);
        if (findUsersById.isEmpty()) {
            log.error("Error");
            throw new Exception("User Not Found", HttpStatus.NOT_FOUND);
        }
        Users updateUsers = findUsersById.get();
        updateUsers.setUserNickname(usersUpdateDTO.getUserNickname());
        updateUsers.setUserPhoto(usersUpdateDTO.getUserPhoto());
        updateUsers.setUserShortBio(usersUpdateDTO.getUserShortBio());
        log.info("Update Successful");
        usersRepository.save(updateUsers);
        throw new Exception("Users Update Successful", HttpStatus.OK);
    }

}
