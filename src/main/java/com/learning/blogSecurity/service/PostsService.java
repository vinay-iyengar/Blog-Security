package com.learning.blogSecurity.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.blogSecurity.ResourceNotFoundException;
import com.learning.blogSecurity.model.Posts;
import com.learning.blogSecurity.repository.PostsRepository;
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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;

@Service
public class PostsService {

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    UsersRepository usersRepository;

    private EncryptDecryptUtils encryptDecryptUtils = new EncryptDecryptUtils();

    public String getPostsByUser(@PathVariable(value = "userId") Long userId) throws JsonProcessingException, NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, CertificateException {
        List<Posts> response = postsRepository.findByUserId(userId);

        ObjectMapper mapper = new ObjectMapper();
        String responseJson = mapper.writeValueAsString(response);
        System.out.println("Resulting JSON String = " + responseJson);

        return encryptDecryptUtils.encrypt(responseJson);
    }

    public Posts createPost(@PathVariable(value = "userId") Long userId, @Validated @RequestBody Posts posts) throws ResourceNotFoundException {
        return usersRepository.findById(userId).map(post -> {
            posts.setUser(post);
            return postsRepository.save(posts);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public Posts updatePost(@PathVariable(value = "userId") Long userId,
                            @PathVariable(value = "postId") Long postId, @Validated @RequestBody Posts addPost)
            throws ResourceNotFoundException {
        if (!usersRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found");
        }

        return postsRepository.findById(postId).map(course -> {
            course.setPostName(addPost.getPostName());
            course.setPostDescription(addPost.getPostDescription());
            course.setPostedDate(addPost.getPostedDate());
            return postsRepository.save(course);
        }).orElseThrow(() -> new ResourceNotFoundException("Post Id not found"));
    }

    public String getAllPosts() throws JsonProcessingException, NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, CertificateException {
        List<Posts> response = postsRepository.findAll();

        ObjectMapper mapper = new ObjectMapper();
        String responseJson = mapper.writeValueAsString(response);
        System.out.println("Resulting JSON String = " + responseJson);

        return encryptDecryptUtils.encrypt(responseJson);
    }

    public ResponseEntity< Posts > getPostsById(
            @PathVariable(value = "id") Long postId) throws ResourceNotFoundException {
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found :: " + postId));
        return ResponseEntity.ok().body(post);
    }

    public ResponseEntity < Posts > updatePostWithUser(
            @PathVariable(value = "id") Long postId,
            @Validated @RequestBody Posts userDetails) throws ResourceNotFoundException {
        Posts user = postsRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found :: " + postId));
        user.setPostName(userDetails.getPostName());
        user.setPostDescription(userDetails.getPostDescription());
        user.setPostedDate(userDetails.getPostedDate());
        final Posts updatedUser = postsRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    public String deletePost(@PathVariable (value = "userId") Long userId,
                             @PathVariable (value = "postId") Long postId) throws ResourceNotFoundException {
        return postsRepository.findByPostIdAndUserId(postId, userId).map(comment -> {
            postsRepository.delete(comment);
            return "Successfully Deleted";
        }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + postId + " and postId " + userId));
    }

    public String deletePostByPostId(@PathVariable (value = "postId") Long postId) throws ResourceNotFoundException {
        return postsRepository.findByPostId(postId).map(comment -> {
            postsRepository.delete(comment);
            return "Successfully Deleted";
        }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + postId));
    }
}
