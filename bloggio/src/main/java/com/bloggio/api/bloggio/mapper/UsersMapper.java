package com.bloggio.api.bloggio.mapper;

import org.mapstruct.Mapper;

import com.bloggio.api.bloggio.dto.UsersDTO;
import com.bloggio.api.bloggio.persistence.entity.Users;

@Mapper
public interface UsersMapper {

    Users usersDTOToUsers(UsersDTO usersDTO);

    UsersDTO usersToUsersDTO(Users users);

}
