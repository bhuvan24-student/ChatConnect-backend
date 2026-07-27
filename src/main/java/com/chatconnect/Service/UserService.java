package com.chatconnect.Service;

import com.chatconnect.Users.User;
import com.chatconnect.repository.UserRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    final UserRepo userRepo;
    public UserService(UserRepo userRepo){
        this.userRepo=userRepo;
    }

    public ResponseEntity<?> createuser(User user){
        try{
           return  ResponseEntity.ok(userRepo.save(user));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
