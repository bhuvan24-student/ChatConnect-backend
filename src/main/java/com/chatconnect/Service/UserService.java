package com.chatconnect.Service;

import com.chatconnect.Security.SecurityConfig;
import com.chatconnect.Users.User;
import com.chatconnect.dto.UserRequestDTO;
import com.chatconnect.dto.UserResponseDTO;
import com.chatconnect.exceptions.UserNotFoundException;
import com.chatconnect.repository.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    final PasswordEncoder passwordEncoder;
    final UserRepo userRepo;
    public UserService(PasswordEncoder passwordEncoder, UserRepo userRepo){
        this.passwordEncoder = passwordEncoder;
        this.userRepo=userRepo;
    }

    public UserResponseDTO usercreation(UserRequestDTO userRequestDTO){
        User user=new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        User Saveduser=userRepo.save(user);
        UserResponseDTO userresponse=new UserResponseDTO();
        userresponse.setName(Saveduser.getName());
        userresponse.setEmail(Saveduser.getEmail());
        userresponse.setId(Saveduser.getId());
        return userresponse;
    }

    public List<UserResponseDTO> getUsers(){
        List<User>users=userRepo.findAll();
        List<UserResponseDTO>list=new ArrayList<>();
        for(User user:users){
            UserResponseDTO responseuser=new UserResponseDTO();
            responseuser.setId(user.getId());
            responseuser.setName(user.getName());
            responseuser.setEmail(user.getEmail());
            list.add(responseuser);
        }
        return list;
    }

    public UserResponseDTO getUserById(Long id){
        Optional<User> user=userRepo.findById(id);
        UserResponseDTO userdto=new UserResponseDTO();
        if(user.isPresent()){
            User users=user.get();
            userdto.setEmail(users.getEmail());
            userdto.setName(users.getName());
            userdto.setId(users.getId());
            return userdto;
        }
        throw new UserNotFoundException(id);
    }

    public UserResponseDTO updateUser(Long id,UserRequestDTO userRequestDTO){
        Optional<User> userdata=userRepo.findById(id);
        if(userdata.isPresent()){
            User existingUser=userdata.get();
            existingUser.setName(userRequestDTO.getName());
            existingUser.setEmail(userRequestDTO.getEmail());
            existingUser.setPassword(userRequestDTO.getPassword());
            userRepo.save(existingUser);
            UserResponseDTO userResponseDTO=new UserResponseDTO();
            userResponseDTO.setEmail(existingUser.getEmail());
            userResponseDTO.setName(existingUser.getName());
            userResponseDTO.setId(existingUser.getId());
            return userResponseDTO;
        }
        throw new UserNotFoundException(id);
    }

    public boolean deleteUser(Long id){
        Optional<User> user=userRepo.findById(id);
        if(user.isPresent()){
            userRepo.deleteById(id);
            return true;
        }
        return false;
    }

    //user login
    public boolean login(String email,String password){
        Optional<User> user=userRepo.findByEmail(email);
        if(user.isPresent()){
           return  passwordEncoder.matches(password,user.get().getPassword());
        }
        return false;
    }
}
