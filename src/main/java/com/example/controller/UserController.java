package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     public  String addNewUser (@RequestParam String firstName
    		      , @RequestParam String lastName,@RequestParam String address
    		      , @RequestParam String password,@RequestParam String phoneNo
    		      , @RequestParam String emailId) 
    {
       
//        String emailId = body.get("emailId");
//        String Address = body.get("address");
//        String password = body.get("password");
//        String lastName = body.get("lastName");
//        String firstName = body.get("firstName");
//        String phoneNo = body.get("phoneNo");
        
        User localUser = new User();
        localUser.setFirstName(firstName);
        localUser.setLastName(lastName);
        localUser.setAddress(address);
        localUser.setPassword(password);
        localUser.setPhoneNo(phoneNo);
        localUser.setEmailId(emailId);
        userRepository.save(localUser);
        
        return "created";
    }

    
    @PostMapping("/create1")
    public  String addNewUser1 (@RequestBody User u1) 
   {
      userRepository.save(u1);
       
       return "created";
   }

	
}
