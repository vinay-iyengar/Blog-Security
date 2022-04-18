package com.learning.blogSecurity.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learning.blogSecurity.ResourceNotFoundException;
import com.learning.blogSecurity.model.Posts;
import com.learning.blogSecurity.service.PostsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@RestController
@RequestMapping("/api/v1/blog")
public class PostsController {

    @Autowired
    PostsService postsService;

    @ApiOperation(value = "Get Posts for a User", response = Posts.class, authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("/users/{userId}/posts")
    public String getPostsByUser(@PathVariable(value = "userId") Long userId) throws JsonProcessingException, NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, CertificateException {
        return postsService.getPostsByUser(userId);
    }

    @ApiOperation(value = "Create Post for a User", response = Posts.class, authorizations = { @Authorization(value="jwtToken") })
    @PostMapping("/users/{userId}/posts")
    public Posts createPost(@PathVariable(value = "userId") Long userId, @Validated @RequestBody Posts posts) throws ResourceNotFoundException {
        return  postsService.createPost(userId, posts);
    }

    @ApiOperation(value = "Update Posts for a User", response = Posts.class, authorizations = { @Authorization(value="jwtToken") })
    @PutMapping("/users/{userId}/posts/{postId}")
    public Posts updatePost(@PathVariable(value = "userId") Long userId,
                                 @PathVariable(value = "postId") Long postId, @Validated @RequestBody Posts addPost)
            throws ResourceNotFoundException {
        return postsService.updatePost(userId, postId, addPost);
    }

    @ApiOperation(value = "Get All Posts", response = Posts.class, authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("/posts")
    public String getAllPosts() throws JsonProcessingException, NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, CertificateException {
        return postsService.getAllPosts();
    }

    @ApiOperation(value = "Get All Posts with PostID", response = Posts.class, authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("/posts/{id}")
    public ResponseEntity< Posts > getPostsById(
            @PathVariable(value = "id") Long postId) throws ResourceNotFoundException {
        return postsService.getPostsById(postId);
    }

    @ApiOperation(value = "Update Posts with PostID", response = Posts.class, authorizations = { @Authorization(value="jwtToken") })
    @PutMapping("/posts/{id}")
    public ResponseEntity < Posts > updatePostWithUser(
            @PathVariable(value = "id") Long postId,
            @Validated @RequestBody Posts userDetails) throws ResourceNotFoundException {
        return postsService.updatePostWithUser(postId, userDetails);
    }

    @ApiOperation(value = "Delete Posts for a User with UserID and PostID", authorizations = { @Authorization(value="jwtToken") })
    @DeleteMapping("/users/{userId}/posts/{postId}")
    public String deletePost(@PathVariable (value = "userId") Long userId,
                                           @PathVariable (value = "postId") Long postId) throws ResourceNotFoundException {
        return postsService.deletePost(userId, postId);
    }

    @ApiOperation(value = "Delete Posts using  PostID", authorizations = { @Authorization(value="jwtToken") })
    @DeleteMapping("/posts/{postId}")
    public String deletePostByPostId(@PathVariable (value = "postId") Long postId) throws ResourceNotFoundException {
        return postsService.deletePostByPostId(postId);
    }
}
