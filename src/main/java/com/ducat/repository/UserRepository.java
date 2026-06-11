package com.ducat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ducat.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
	User findByUsername(String username);
	
}
