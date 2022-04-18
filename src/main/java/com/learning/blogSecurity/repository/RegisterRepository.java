package com.learning.blogSecurity.repository;

import com.learning.blogSecurity.model.RegisterDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterRepository extends JpaRepository<RegisterDAO, Integer> {
    RegisterDAO findByUsername(String username);
}
