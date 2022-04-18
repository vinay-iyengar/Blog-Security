package com.learning.blogSecurity.service;

import com.learning.blogSecurity.ResourceNotFoundException;
import com.learning.blogSecurity.model.Comments;
import com.learning.blogSecurity.repository.CommentsRepository;
import com.learning.blogSecurity.repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Service
public class CommentsService {

    @Autowired
    CommentsRepository commentsRepository;

    @Autowired
    PostsRepository postsRepository;

    public Comments createComment(@PathVariable(value = "postId") Long postId, @Validated @RequestBody Comments comments) throws ResourceNotFoundException {
        return postsRepository.findById(postId).map(post -> {
            comments.setPost(post);
            return commentsRepository.save(comments);
        }).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }

    public Comments updateComment(@PathVariable(value = "postId") Long postId,
                                  @PathVariable(value = "commentId") Long commentId, @Validated @RequestBody Comments updateComment)
            throws ResourceNotFoundException {
        if (!postsRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found");
        }

        return commentsRepository.findById(commentId).map(course -> {
            course.setText(updateComment.getText());
            return commentsRepository.save(course);
        }).orElseThrow(() -> new ResourceNotFoundException("Comment Id not found"));
    }

    public List<Comments> getAllComments() {
        return commentsRepository.findAll();
    }

    public ResponseEntity< Comments > getCommentsById(
            @PathVariable(value = "id") Long commentId) throws ResourceNotFoundException {
        Comments post = commentsRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found : " + commentId));
        return ResponseEntity.ok().body(post);
    }

    public ResponseEntity < Comments > updateComment(
            @PathVariable(value = "id") Long postId,
            @Validated @RequestBody Comments userDetails) throws ResourceNotFoundException {
        Comments user = commentsRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found : " + postId));
        user.setText(userDetails.getText());
        final Comments updatedUser = commentsRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    public String deleteComments(@PathVariable (value = "commentId") Long commentId) throws ResourceNotFoundException {
        return commentsRepository.findByCommentId(commentId).map(comment -> {
            commentsRepository.delete(comment);
            return "Successfully Deleted";
        }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId));
    }
}
