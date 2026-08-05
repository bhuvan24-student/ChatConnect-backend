package com.chatconnect.Controllers;

import com.chatconnect.Service.UserService;
import com.chatconnect.Users.User;
import com.chatconnect.dto.UserRequestDTO;
import com.chatconnect.dto.UserResponseDTO;
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
    public ResponseEntity<UserResponseDTO> usercreation(@RequestBody UserRequestDTO user){
        UserResponseDTO userResponseDTO=userService.usercreation(user);
         return ResponseEntity.ok(userResponseDTO);
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
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user){
        String updated= userService.updateUser(id,user);
        if(updated.equals("Updated")) return ResponseEntity.ok("Updated");
        return ResponseEntity.notFound().build();
    }
    //Delete user by id
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        Optional<User> user=userService.deleteUser(id);
        if(user.isPresent()) return ResponseEntity.ok(user);
        return ResponseEntity.notFound().build();
    }
}
