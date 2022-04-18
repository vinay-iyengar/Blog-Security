package com.learning.blogSecurity.controller;

import com.learning.blogSecurity.ResourceNotFoundException;
import com.learning.blogSecurity.model.Comments;
import com.learning.blogSecurity.service.CommentsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/blog")
public class CommentsController {

    @Autowired
    CommentsService commentsService;

    @ApiOperation(value = "Add Comments for a Post", response = Comments.class, authorizations = { @Authorization(value="jwtToken") })
    @PostMapping("/posts/{postId}/comments")
    public Comments createComment(@PathVariable(value = "postId") Long postId, @Validated @RequestBody Comments comments) throws ResourceNotFoundException {
        return commentsService.createComment(postId, comments);
    }

    @ApiOperation(value = "Update Comments for a Post", response = Comments.class, authorizations = { @Authorization(value="jwtToken") })
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public Comments updateComment(@PathVariable(value = "postId") Long postId,
                            @PathVariable(value = "commentId") Long commentId, @Validated @RequestBody Comments updateComment)
            throws ResourceNotFoundException {
        return commentsService.updateComment(postId, commentId, updateComment);
    }

    @ApiOperation(value = "Get All Comments", response = Comments.class, authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("/comments")
    public List<Comments> getAllComments() {
        return commentsService.getAllComments();
    }

    @ApiOperation(value = "Get All Comments Using CommentId", response = Comments.class, authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("/comments/{id}")
    public ResponseEntity< Comments > getCommentsById(
            @PathVariable(value = "id") Long commentId) throws ResourceNotFoundException {
        return commentsService.getCommentsById(commentId);
    }

    @ApiOperation(value = "Update Comments using Comment ID", response = Comments.class, authorizations = { @Authorization(value="jwtToken") })
    @PutMapping("/comments/{id}")
    public ResponseEntity < Comments > updateComment(
            @PathVariable(value = "id") Long postId,
            @Validated @RequestBody Comments userDetails) throws ResourceNotFoundException {
        return commentsService.updateComment(postId, userDetails);
    }

    @ApiOperation(value = "Delete Comments using CommentId", response = Comments.class, authorizations = { @Authorization(value="jwtToken") })
    @DeleteMapping("/comments/{commentId}")
    public String deleteComments(@PathVariable (value = "commentId") Long commentId) throws ResourceNotFoundException {
        return commentsService.deleteComments(commentId);
    }
}
