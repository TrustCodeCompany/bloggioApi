package com.bloggio.api.bloggio.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.mapstruct.factory.Mappers;

import com.bloggio.api.bloggio.dto.UsersDTO;
import com.bloggio.api.bloggio.persistence.entity.Users;

public class UsersMapperTest {

    private UsersMapper mapper = Mappers.getMapper(UsersMapper.class);

    @Test
    public void usersSimpleTest() {

        Users users = new Users();
        users.setUserNickname("Piero");
        users.setUserPassword("Shalon25cm");
        users.setUserPhoto("Shalon.png");
        users.setUserShortBio("El mas shalon del grupo");

        UsersDTO usersDTO = mapper.usersToUsersDTO(users);

        System.out.println(users);
        System.out.println(usersDTO);

        assertEquals(usersDTO.getUserNickname(), users.getUserNickname());
    }
}
