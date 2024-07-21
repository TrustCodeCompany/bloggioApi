package com.bloggio.api.bloggio.service;

import com.bloggio.api.bloggio.dto.UsersDTO;
import com.bloggio.api.bloggio.mapper.UsersMapperImpl;
import com.bloggio.api.bloggio.persistence.entity.Users;
import com.bloggio.api.bloggio.persistence.repository.UsersRepository;

import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Collectors;

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

    /*public UsersDTO create(UsersDTO usersDTO) {
        Users users = usersMapperImpl.usersDTOToUsers(usersDTO);
        log.info("Create User Successful");
        return usersMapperImpl.usersToUsersDTO(usersRepository.save(users));
    }*/

    public List<UsersDTO> getAll() {
        log.info("Get All Users");
        return usersMapperImpl.ListUsersToListUsersDTO(usersRepository.findAll());
    }

    public List<UsersDTO> getAllActivated() {
        log.info("Get All Activated Users");
        return usersMapperImpl.ListUsersToListUsersDTO(
                usersRepository.findAll().stream().filter(item -> item.getUserState() == 1)
                        .collect(Collectors.toList()));
    }

    public List<UsersDTO> getAllDeactivated() {
        log.info("Get All Deactivated Users");
        return usersMapperImpl.ListUsersToListUsersDTO(usersRepository.findAll().stream()
                .filter(item -> item.getUserState() == 0).collect(Collectors.toList()));
    }

    public void activateAccount(String id, int status){
        log.info("update status account");
        usersRepository.updateStateUser(id, status);
    }

}
