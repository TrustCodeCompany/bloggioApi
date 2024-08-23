package com.bloggio.api.bloggio.security.services;

import com.bloggio.api.bloggio.persistence.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bloggio.api.bloggio.persistence.entity.Users;
import com.bloggio.api.bloggio.persistence.repository.UsersRepository;

import java.math.BigDecimal;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsersRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userNickname) throws UsernameNotFoundException {
        Users user = userRepository.findByUserNickname(userNickname)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with User Nickname: " + userNickname));

        if (user.getUserState() == BigDecimal.ZERO.intValue()){
            this.userRepository.updateStateUser(user.getUserId(), BigDecimal.ONE.intValue());
            this.postRepository.disabledPostByUserId(user.getUserId(), BigDecimal.ONE.intValue());
            user.setReactiveAccount(true);
        }else {
            user.setReactiveAccount(false);
        }

        return UserDetailsImpl.build(user);
    }

}
