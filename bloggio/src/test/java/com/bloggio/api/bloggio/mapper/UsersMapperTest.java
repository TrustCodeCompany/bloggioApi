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
        users.setUserEmail("piero@gmail.com");
        users.setUserNickname("Piero2023");
        users.setUserPassword("PieroBecerra");
        users.setUserPhoto("Piero.png");
        users.setUserShortBio("Perfil De Piero");

        UsersDTO usersDTO = mapper.usersToUsersDTO(users);

        System.out.println(users);
        System.out.println(usersDTO);

        assertEquals(usersDTO.getUserNickname(), users.getUserNickname());
    }
}
