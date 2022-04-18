package com.learning.blogSecurity.repository;

import com.learning.blogSecurity.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    List<Posts> findByUserId(Long userId);

    Optional<Posts> findByPostIdAndUserId(Long postId, Long userId);

    Optional<Posts> findByPostId(Long postId);
}
