package com.bloggio.api.bloggio.service;

import org.springframework.stereotype.Service;

import com.bloggio.api.bloggio.dto.UsersDTO;
import com.bloggio.api.bloggio.mapper.UsersMapperImpl;
import com.bloggio.api.bloggio.persistence.entity.Users;
import com.bloggio.api.bloggio.persistence.repository.UsersRepository;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final UsersMapperImpl usersMapperImpl;

    public UsersService(UsersRepository usersRepository, UsersMapperImpl usersMapperImpl) {
        this.usersRepository = usersRepository;
        this.usersMapperImpl = usersMapperImpl;
    }

    public UsersDTO create(UsersDTO usersDTO) {
        Users users = usersMapperImpl.usersDTOToUsers(usersDTO);
        return usersMapperImpl.usersToUsersDTO(usersRepository.save(users));
    }

}
