package com.learning.blogSecurity.repository;

import com.learning.blogSecurity.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    Optional<Comments> findByCommentId(Long commentId);
}
