package com.bloggio.api.bloggio.controller;

import com.bloggio.api.bloggio.dto.UsersDTO;
import com.bloggio.api.bloggio.service.UsersService;

import io.swagger.annotations.Authorization;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/GetAll")
    public ResponseEntity<List<UsersDTO>> GetAll() {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getAll());
    }

    //@PreAuthorize("hasRole('ROLE_USER') or ('ROLE_ADMIN')")
    @GetMapping("/GetAllActivated")
    public ResponseEntity<List<UsersDTO>> GetAllActivated() {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getAllActivated());
    }

    //@PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/GetAllDeactivated")
    public ResponseEntity<List<UsersDTO>> GetAllDeactivated() {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getAllDeactivated());
    }

    /*@PutMapping("/update-account/{id}")
    public ResponseEntity<UsersDTO> activateAccount(
            @PathVariable("id") String id,
            @RequestParam("status") int status){
        usersService.activateAccount(id, status);
        return ResponseEntity.status(HttpStatus.OK).build();
    }*/

}
