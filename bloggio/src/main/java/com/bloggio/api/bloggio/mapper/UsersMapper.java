package com.bloggio.api.bloggio.mapper;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import com.bloggio.api.bloggio.dto.UsersDTO;
import com.bloggio.api.bloggio.persistence.entity.Users;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UsersMapper {

    Users usersDTOToUsers(UsersDTO usersDTO);

    UsersDTO usersToUsersDTO(Users users);

    List<Users> ListUsersDTOToListUsers(List<UsersDTO> ListUsersDTO);

    List<UsersDTO> ListUsersToListUsersDTO(List<Users> ListUsers);

}
