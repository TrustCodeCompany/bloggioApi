package com.bloggio.api.bloggio.service;

import org.springframework.stereotype.Service;

import com.bloggio.api.bloggio.persistence.repository.UsersRepository;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

}
