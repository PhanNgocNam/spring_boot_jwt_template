package com.template.jwt.services;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.template.jwt.models.UserEntity;
import com.template.jwt.repositoties.UserRepository;

@Service
public class CustomeUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException("Username not found: " + username);
        UserEntity userEntity = user.get();
        return new User(userEntity.getUsername(), userEntity.getPassword(), new ArrayList<>());
    }

}
