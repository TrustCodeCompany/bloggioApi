package com.bloggio.api.bloggio.service;

import com.bloggio.api.bloggio.dto.UsersDTO;
import com.bloggio.api.bloggio.mapper.UsersMapperImpl;
import com.bloggio.api.bloggio.persistence.entity.Users;
import com.bloggio.api.bloggio.persistence.repository.UsersRepository;

import lombok.extern.log4j.Log4j2;

import com.bloggio.api.bloggio.exception.Exception;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UsersService {

    private final UsersRepository usersRepository;

    private UsersMapperImpl usersMapperImpl;

    public UsersService(UsersRepository usersRepository, UsersMapperImpl usersMapperImpl) {
        this.usersRepository = usersRepository;
        this.usersMapperImpl = usersMapperImpl;
    }

    public UsersDTO create(UsersDTO usersDTO) {
        Users users = usersMapperImpl.usersDTOToUsers(usersDTO);
        log.info("Create User Succesful");
        return usersMapperImpl.usersToUsersDTO(usersRepository.save(users));
    }

    public List<UsersDTO> getAll() {
        log.info("Get All Users");
        return usersMapperImpl.ListUsersToListUsersDTO(usersRepository.findAll());
    }

    public void deleteByUserId(UUID userId) {
        Optional<Users> findUsersByUserId = usersRepository.findById(userId);
        if (!findUsersByUserId.isPresent()) {
            log.error("User With Id Not Found");
            throw new Exception("User Not Found", HttpStatus.NOT_FOUND);
        }
        usersRepository.deleteById(userId);
        throw new Exception("User Removed Successful", HttpStatus.OK);
    }

    // login (jwt - spring security)
    // reestablecer password
    // actualizar informacion (nickname , short bio , la foto)
    // cambiar contraseña

}
