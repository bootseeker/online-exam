package com.example.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.ResourceExistsException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.ResponseMessage;
import com.example.model.Roles;
import com.example.model.User;
import com.example.repository.RoleRespository;
import com.example.repository.UserRepository;

@RestController
@RequestMapping("/api/exam/v1")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRespository roleRespository;
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = new ArrayList<>();
		users = userRepository.findByisActive(true);

		return ResponseEntity.ok(users);
	}

	@PostMapping("/users")
	public ResponseEntity<ResponseMessage> createUser(@RequestBody User user) {
		User existingUser = userRepository.findByEmailId(user.getEmailId());

		if (existingUser != null) {
			throw new ResourceExistsException("User already exists with email :" + user.getEmailId());
		}
		 Roles userRole = roleRespository.findByRoleName("Examiner");
		
		user.setCreatedBy("SYSTEM");
		user.setCreatedDate(new Date());
		user.setActive(true);
		user.setRole(userRole);
		userRepository.save(user);
		ResponseMessage message = new ResponseMessage("User Created Successfully");
		return ResponseEntity.ok().body(message);
	}

	@GetMapping("/users/{email}")
	public ResponseEntity<User> getUsersByEmailId(@PathVariable String email) {
		User user = new User();
		user = userRepository.findByEmailId(email);

		return ResponseEntity.ok().body(user);
	}

	@PutMapping("/users")
	public ResponseEntity<ResponseMessage> updateUser(@RequestBody User updatedUser) throws ResourceNotFoundException {

		long localUserId = 0;
		
		User dbUser = userRepository.findByEmailId(updatedUser.getEmailId());

		if (dbUser == null) {
			throw new ResourceNotFoundException("User not found with email : " + updatedUser.getEmailId());
		}
		localUserId = dbUser.getUserId();
		updatedUser.setUserId(localUserId);
		updatedUser.setCreatedBy(dbUser.getCreatedBy());
		updatedUser.setCreatedDate(dbUser.getCreatedDate());
		updatedUser.setRole(dbUser.getRole());
		BeanUtils.copyProperties(updatedUser,dbUser);
		
		updatedUser.setUpdatedBy("SYSTEM");
		updatedUser.setUpdatedDate(new Date());
		userRepository.save(updatedUser);
		ResponseMessage message = new ResponseMessage("User Updated Successfully");
		return ResponseEntity.ok().body(message);
	}

	@DeleteMapping("/users/{email}")
	public ResponseEntity<ResponseMessage> deleteUser(@PathVariable String email) throws ResourceNotFoundException {
		User dbUser = userRepository.findByEmailId(email);
		if (dbUser == null) {
			throw new ResourceNotFoundException("User not found with email : " + email);
		}
		dbUser.setActive(false);
		userRepository.save(dbUser);
		ResponseMessage message = new ResponseMessage("User deactivated Successfully");
		return ResponseEntity.ok().body(message);

	}

}
