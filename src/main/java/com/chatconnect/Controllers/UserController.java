package com.chatconnect.Controllers;

import com.chatconnect.Service.UserService;
import com.chatconnect.Users.User;
import com.chatconnect.dto.LoginRequestDTO;
import com.chatconnect.dto.UserRequestDTO;
import com.chatconnect.dto.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    final UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }
    //To create new user
    @PostMapping("/createuser")
    public ResponseEntity<UserResponseDTO> usercreation(@Valid @RequestBody UserRequestDTO user){
        UserResponseDTO userResponseDTO=userService.usercreation(user);
         return ResponseEntity.ok(userResponseDTO);
    }


    //login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginreq){
        boolean user= userService.login(loginreq.getEmail(),loginreq.getPassword());
        if(user) return ResponseEntity.ok().body("Succesfully logged in");
        return ResponseEntity.status(401).body("Invalid Email Or Password");
    }

    //To retrive all the data
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }


    //to get user data by id
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){
         UserResponseDTO userResponseDTO=userService.getUserById(id);
         if(userResponseDTO!=null) {
             return ResponseEntity.ok(userResponseDTO);
         }
         return ResponseEntity.notFound().build();
    }

    //To update the existing data
    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id,@Valid @RequestBody UserRequestDTO user){
        UserResponseDTO userResponseDTO=userService.updateUser(id,user);
        if(userResponseDTO!=null){
            return ResponseEntity.ok(userResponseDTO);
        }
        return ResponseEntity.notFound().build();
    }
    //Delete user by id
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
         if(userService.deleteUser(id)){
             return ResponseEntity.noContent().build();
         }
         return ResponseEntity.notFound().build();
    }
}
