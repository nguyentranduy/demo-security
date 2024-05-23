package com.m2m.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.m2m.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);
}
