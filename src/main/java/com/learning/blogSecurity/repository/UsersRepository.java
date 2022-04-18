package com.learning.blogSecurity.repository;

import com.learning.blogSecurity.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
