package com.learning.blogSecurity.service;

import com.learning.blogSecurity.model.AuthenticationRequest;
import com.learning.blogSecurity.model.AuthenticationResponse;
import com.learning.blogSecurity.model.RegisterDAO;
import com.learning.blogSecurity.model.RegisterDTO;
import com.learning.blogSecurity.repository.RegisterRepository;
import com.learning.blogSecurity.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUserDetailsService jwtUserDetailsService;

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private JWTUtil jwtTokenUtil;

    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    public ResponseEntity<?> saveUser(@RequestBody RegisterDTO user) {
        return ResponseEntity.ok(jwtUserDetailsService.save(user));
    }

    public List<RegisterDAO> getRegisterList() {
        return registerRepository.findAll();
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
