package com.learning.blogSecurity.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.blogSecurity.ResourceNotFoundException;
import com.learning.blogSecurity.model.Users;
import com.learning.blogSecurity.repository.UsersRepository;
import com.learning.blogSecurity.util.EncryptDecryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UsersRepository usersRepository;

    private EncryptDecryptUtils encryptDecryptUtils = new EncryptDecryptUtils();

    public String getUsers() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, CertificateException, BadPaddingException, IllegalBlockSizeException, JsonProcessingException {
        List<Users> response = (usersRepository.findAll());

        ObjectMapper mapper = new ObjectMapper();
        String responseJson = mapper.writeValueAsString(response);
        System.out.println("Resulting JSON String = " + responseJson);

        return encryptDecryptUtils.encrypt(responseJson);
    }

    public ResponseEntity< Users > getUserById(
            @PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userId));
        return ResponseEntity.ok().body(user);
    }

    public String createUser(@Validated @RequestBody String user) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException, CertificateException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        String requestFromClient = encryptDecryptUtils.decrypt(user);
        ObjectMapper mapper = new ObjectMapper();

        Users addUser = mapper.readValue(requestFromClient, Users.class);
        Users newUser  = usersRepository.save(addUser);

        String json = mapper.writeValueAsString(newUser);
        System.out.println("Resulting JSONs String = " + json);

        return encryptDecryptUtils.encrypt(json);
    }

    public ResponseEntity < Users > updateUser(
            @PathVariable(value = "id") Long userId,
            @Validated @RequestBody Users userDetails) throws ResourceNotFoundException {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userId));
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmailId(userDetails.getEmailId());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        final Users updatedUser = usersRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    public Map< String, Boolean > deleteUser(
            @PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        Users instructor = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userId));

        usersRepository.delete(instructor);
        Map < String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
