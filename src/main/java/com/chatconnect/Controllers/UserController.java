package com.chatconnect.Controllers;

import com.chatconnect.Service.UserService;
import com.chatconnect.Users.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    final UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/createuser")
    public ResponseEntity<?> usercreation(@RequestBody User user){
       return userService.createuser(user);
    }
}
