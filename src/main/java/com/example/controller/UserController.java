package com.example.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.SpringBootException;
import com.example.model.User;
import com.example.repository.UserRepository;

@RestController
@RequestMapping("/api/exam/v1")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers()
	{
		List<User> users = new ArrayList<>();
		users = userRepository.findAll();
		
		return ResponseEntity.ok(users);
	}    
    @PostMapping("/create")
   public  ResponseEntity<String> addNewUser(@Valid@RequestBody User u1) 
   {
    	String localStrEmailId = null;
    	List<User> users = new ArrayList<>();
    	localStrEmailId = u1.getEmailId();
    	localStrEmailId = nulltoEmptyString(localStrEmailId);
    	
		users = userRepository.findByEmailId(localStrEmailId);
		
		if(users.size()>0)
		{
			throw new SpringBootException("E-Mail id already exists");
		}
    	u1.setCreatedBy("SYSTEM");
    	u1.setCreatedDate(new Date());
    	 userRepository.save(u1);
		return new ResponseEntity("Created Sucessfully", HttpStatus.OK);
   }

	
	@GetMapping("/users/{emailId}")
	public ResponseEntity<List<User>> getUsersByEmailId(@PathVariable String emailId)
	{
		List<User> users = new ArrayList<>();
		users = userRepository.findByEmailId(emailId);
		
		return new ResponseEntity(users,HttpStatus.OK);
	}
	
	@PostMapping("/update")
	public ResponseEntity<String> upadteUser(@RequestBody User u1) 
	{
		String responseMessage = null;
		long userId = u1.getUserId();
		boolean localFlag = false;

		User dbUser = userRepository.findByUserId(userId);

		if(dbUser==null)
		{
			throw new SpringBootException("Record is deleted concurrently");
		}
		
		if (!dbUser.getEmailId().equalsIgnoreCase(u1.getEmailId())) {
			if (nulltoEmptyString(u1.getEmailId()).length() > 0) {
				dbUser.setEmailId(u1.getEmailId());
				localFlag = true;
			}
		}
		if (!dbUser.getAddress().equalsIgnoreCase(u1.getAddress())) {
			if (nulltoEmptyString(u1.getAddress()).length() > 0) {
				dbUser.setAddress(u1.getAddress());
				localFlag = true;
			}
		}
		if (!dbUser.getPhoneNo().equalsIgnoreCase(u1.getPhoneNo())) {
			if (nulltoEmptyString(u1.getPhoneNo()).length() > 0) {
				dbUser.setPhoneNo(u1.getPhoneNo());
				localFlag = true;
			}
		}
		if (!dbUser.getFirstName().equalsIgnoreCase(u1.getFirstName())) {
			if (nulltoEmptyString(u1.getFirstName()).length() > 0) {
				dbUser.setFirstName(u1.getFirstName());
				localFlag = true;
			}
		}
		if (!dbUser.getLastName().equalsIgnoreCase(u1.getLastName())) {
			if (nulltoEmptyString(u1.getLastName()).length() > 0) {
				dbUser.setLastName(u1.getLastName());
				localFlag = true;
			}
		}
		if (!dbUser.getPassword().equalsIgnoreCase(u1.getPassword())) {
			if (nulltoEmptyString(u1.getPassword()).length() > 0) {
				dbUser.setPassword(u1.getPassword());
				localFlag = true;
			}
		}
		if (u1.getIsActive() && !dbUser.getIsActive()) {
			dbUser.setIsActive(u1.getIsActive());
			localFlag = true;

		} else if (!u1.getIsActive() && dbUser.getIsActive()) {
			dbUser.setIsActive(u1.getIsActive());
			localFlag = true;
		}
		if (localFlag) {
			dbUser.setUpdatedBy("SYSTEM");
			dbUser.setUpdatedDate(new Date());
			userRepository.save(dbUser);
		}
		if (localFlag) 
		{
			responseMessage = "updated";
		} else {
			responseMessage = "no changes to save.";
		}
		return  new ResponseEntity(responseMessage, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity deleteUser(@PathVariable long userId)
	{
		User dbUser = userRepository.findByUserId(userId);
		if(dbUser==null)
		{
			throw new SpringBootException("Record is deleted concurrently");
		}
		userRepository.deleteById(userId);
		return new ResponseEntity("Successfully Deleted", HttpStatus.OK);
	}
	public String  nulltoEmptyString(String stringArg)
	{
		String localStrValue = null;
		
		if(stringArg == null)
		{
			localStrValue = "";
		}
		else
		{
			localStrValue = stringArg.trim();
		}
		
		return localStrValue;
	}

}
