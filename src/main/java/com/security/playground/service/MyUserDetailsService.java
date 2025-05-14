package com.security.playground.service;

import com.security.playground.model.Users;
import com.security.playground.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public MyUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> users = usersRepository.findByUsername(username);
        if (users.isPresent()) {
            Users user = users.get();
            return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole().split(","))
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
