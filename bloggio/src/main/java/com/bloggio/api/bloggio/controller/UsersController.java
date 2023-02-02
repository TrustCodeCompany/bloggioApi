package com.bloggio.api.bloggio.controller;

import com.bloggio.api.bloggio.dto.UsersDTO;
import com.bloggio.api.bloggio.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/Create")
    public ResponseEntity<UsersDTO> Create(@RequestBody UsersDTO usersDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usersService.create(usersDTO));
    }

}
