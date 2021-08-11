package com.javadev.jwtUserApi.repo;

import com.javadev.jwtUserApi.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  {
    User findByUserName(String username);
}
