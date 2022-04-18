package com.learning.blogSecurity.controller;

import com.learning.blogSecurity.model.*;
import com.learning.blogSecurity.service.AuthenticationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @ApiOperation(value = "Create Authentication Token", response = AuthenticationRequest.class)
    @RequestMapping(value = "/api/v1/blog/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return authenticationService.createAuthenticationToken(authenticationRequest);
    }

    @ApiOperation(value = "Create User for Authentication", response = RegisterDTO.class)
    @RequestMapping(value = "/api/v1/blog/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody RegisterDTO user) {
        return authenticationService.saveUser(user);
    }

    @ApiOperation(value = "Get all Authenticated Users", response = RegisterDAO.class)
    @GetMapping("/api/v1/blog/register")
    public List<RegisterDAO> getRegisterList() {
        return authenticationService.getRegisterList();
    }
}
