package com.bloggio.api.bloggio.controller;

import com.bloggio.api.bloggio.dto.UsersDTO;
import com.bloggio.api.bloggio.service.UsersService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(usersService.create(usersDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/GetAll")
    public ResponseEntity<List<UsersDTO>> GetAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usersService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
