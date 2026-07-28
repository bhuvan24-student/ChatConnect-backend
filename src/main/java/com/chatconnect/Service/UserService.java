package com.chatconnect.Service;

import com.chatconnect.Users.User;
import com.chatconnect.repository.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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

    public List<User> getUsers(){
        return userRepo.findAll();
    }

    public Optional<User> getUserById(Long id){
        Optional<User> user=userRepo.findById(id);
        return user;
    }

    public String updateUser(Long id,User user){
        Optional<User> data=userRepo.findById(id);
        if(data.isPresent()){
            user.setId(id);
            userRepo.save(user);
            return "Updated";
        }
        return "Error";
    }

    public Optional<User> deleteUser(Long id){
        Optional<User> user=userRepo.findById(id);
        if(user.isPresent()){
            userRepo.deleteById(id);
            return user;
        }
        return Optional.empty();
    }
}
