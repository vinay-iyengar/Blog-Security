package com.learning.blogSecurity.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learning.blogSecurity.ResourceNotFoundException;
import com.learning.blogSecurity.model.Users;
import com.learning.blogSecurity.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/blog")
public class UsersController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "Get All Users", response = Users.class, authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("/users")
    @ResponseBody
    public String getUsers() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, CertificateException, BadPaddingException, IllegalBlockSizeException, JsonProcessingException {
        return  userService.getUsers();
    }

    @ApiOperation(value = "Get All Users By UserId", response = Users.class, authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("/users/{id}")
    public ResponseEntity< Users > getUserById(
            @PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        return userService.getUserById(userId);
    }

    @ApiOperation(value = "Create User", response = Users.class, authorizations = { @Authorization(value="jwtToken") })
    @PostMapping("/users")
    public String createUser(@Validated @RequestBody String user) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException, CertificateException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return userService.createUser(user);
    }

    @ApiOperation(value = "Update User with UserId", response = Users.class, authorizations = { @Authorization(value="jwtToken") })
    @PutMapping("/users/{id}")
    public ResponseEntity < Users > updateUser(
            @PathVariable(value = "id") Long userId,
            @Validated @RequestBody Users userDetails) throws ResourceNotFoundException {
        return userService.updateUser(userId, userDetails);
    }

    @ApiOperation(value = "Delete User with UserId",  response = Users.class, authorizations = { @Authorization(value="jwtToken") })
    @DeleteMapping("/users/{id}")
    public Map< String, Boolean > deleteUser(
            @PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        return userService.deleteUser(userId);
    }
}
