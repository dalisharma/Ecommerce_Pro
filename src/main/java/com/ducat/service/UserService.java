package com.ducat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ducat.entity.User;
import com.ducat.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public boolean register(User user) {

        // User already exists
        if(repo.findByUsername(user.getUsername()) != null) {

            return false;
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        repo.save(user);

        return true;
    }
    
    //search user
    public List<User> searchUser(String keyword){
    		List<User> allUser=repo.findAll();
    		List<User> result=new ArrayList<>();
    		for(User u:allUser) {
    			if(u.getUsername().toLowerCase().contains(keyword.toLowerCase()))
    				result.add(u);
    		}
    		return result;
    }
    
    //delete user
    public void deleteUser(Long id) {
    		repo.deleteById(id);
    }
}