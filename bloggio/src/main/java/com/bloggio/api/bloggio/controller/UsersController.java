package com.bloggio.api.bloggio.controller;

import com.bloggio.api.bloggio.dto.UsersDTO;
import com.bloggio.api.bloggio.service.UsersService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/Create")
    public ResponseEntity<UsersDTO> Create(@Valid @RequestBody UsersDTO usersDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usersService.create(usersDTO));
    }

    @GetMapping("/GetAll")
    public ResponseEntity<List<UsersDTO>> GetAll() {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getAll());
    }

    @GetMapping("/GetAllActivated")
    public ResponseEntity<List<UsersDTO>> GetAllActivated() {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getAllActivated());
    }

    @GetMapping("/GetAllDeactivated")
    public ResponseEntity<List<UsersDTO>> GetAllDeactivated() {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getAllDeactivated());
    }

}
