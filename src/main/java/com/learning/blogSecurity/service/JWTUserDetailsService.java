package com.learning.blogSecurity.service;

import com.learning.blogSecurity.model.RegisterDAO;
import com.learning.blogSecurity.model.RegisterDTO;
import com.learning.blogSecurity.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JWTUserDetailsService implements UserDetailsService {

    @Autowired
    private RegisterRepository registerDAO;

    @Autowired
    private PasswordEncoder bCryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RegisterDAO user = registerDAO.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public RegisterDAO save(RegisterDTO user) {
        RegisterDAO newUser = new RegisterDAO();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bCryptEncoder.encode(user.getPassword()));
        return registerDAO.save(newUser);
    }
}
