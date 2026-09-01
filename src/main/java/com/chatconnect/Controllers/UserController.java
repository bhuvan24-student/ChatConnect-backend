package com.chatconnect.Controllers;

import com.chatconnect.Service.UserService;
import com.chatconnect.DTO.LoginRequestDTO;
import com.chatconnect.DTO.UserRequestDTO;
import com.chatconnect.DTO.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    //Delete user by id only admins have permision
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
         if(userService.deleteUser(id)){
             return ResponseEntity.noContent().build();
         }
         return ResponseEntity.notFound().build();
    }
}
