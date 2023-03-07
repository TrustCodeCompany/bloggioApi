package com.bloggio.api.bloggio.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bloggio.api.bloggio.persistence.entity.Users;
import com.bloggio.api.bloggio.persistence.repository.UsersRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsersRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userNickname) throws UsernameNotFoundException {
        Users user = userRepository.findByUserNickname(userNickname)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with User Nickname: " + userNickname));

        return UserDetailsImpl.build(user);
    }

}
