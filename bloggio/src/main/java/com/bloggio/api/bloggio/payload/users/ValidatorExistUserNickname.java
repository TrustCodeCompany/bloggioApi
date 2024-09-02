package com.bloggio.api.bloggio.payload.users;

import com.bloggio.api.bloggio.persistence.repository.UsersRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Log4j2
public class ValidatorExistUserNickname implements ConstraintValidator<ExistUserNickname, String> {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public boolean isValid(String nickname, ConstraintValidatorContext context) {

        String currentUsername = getCurrentAuthenticatedUsername();

        // Verifica si el nickname ya existe y no pertenece al usuario actual
        return !usersRepository.existsByUserNickname(nickname) || nickname.equals(currentUsername);

    }

    private String getCurrentAuthenticatedUsername() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName(); // Devuelve el nombre de usuario autenticado
        }

        return null; // O lanza una excepción si lo prefieres
    }

}
