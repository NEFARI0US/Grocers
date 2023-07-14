package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;


public class UserService {

	@Autowired
	UserRepository userrepo;
	public void addUser(User user) {
		userrepo.save(user);
	}
	public List<User> getAllUser(){
		return userrepo.findAll();		
	}
	public void deleteUserById(int id) {
		userrepo.deleteById(id);
		
	}
	public Optional<User> getUserById(int id){
		return userrepo.findById(id);
	}
}
