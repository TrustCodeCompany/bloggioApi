package com.bloggio.api.bloggio.service;

import com.bloggio.api.bloggio.dto.UsersUpdateDTO;
import com.bloggio.api.bloggio.exception.Exception;
import com.bloggio.api.bloggio.mapper.UsersMapperImpl;
import com.bloggio.api.bloggio.persistence.entity.Users;
import com.bloggio.api.bloggio.persistence.repository.UsersRepository;
import com.cloudinary.Cloudinary;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Service
@Log4j2
public class AuthService {

    private final UsersRepository usersRepository;

    private UsersMapperImpl usersMapperImpl;

    @Resource
    private Cloudinary cloudinary;

    public AuthService (UsersRepository usersRepository , UsersMapperImpl usersMapperImpl) {
        this.usersRepository = usersRepository;
        this.usersMapperImpl = usersMapperImpl;
    }

    public void updateById(UsersUpdateDTO usersUpdateDTO, MultipartFile file) {
        Optional<Users> findUsersById = usersRepository.findById(UUID.fromString(usersUpdateDTO.getUserId()));
        String url;
        if (findUsersById.isEmpty()) {
            log.error("Error");
            throw new Exception("User Not Found", HttpStatus.NOT_FOUND);
        }

        Users updateUsers = findUsersById.get();
        url = updateUsers.getUserPhoto();
        if (Objects.nonNull(file) && !file.isEmpty() && file.getSize() > 0) {
            url = uploadFile(file, "bloggio_users");
        }

        updateUsers.setUserNickname(usersUpdateDTO.getUserNickname());
        updateUsers.setUserPhoto(url);
        updateUsers.setUserShortBio(usersUpdateDTO.getUserShortBio());
        usersRepository.save(updateUsers);
        log.info("Update Successful");
        //throw new Exception("Users Update Successful", HttpStatus.OK);
    }

    public String uploadFile(MultipartFile file, String folderName) {
        try {
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            return cloudinary.url().secure(true).generate(publicId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
