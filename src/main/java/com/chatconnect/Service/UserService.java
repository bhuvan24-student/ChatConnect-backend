package com.chatconnect.Service;

import com.chatconnect.Users.User;
import com.chatconnect.dto.UserRequestDTO;
import com.chatconnect.dto.UserResponseDTO;
import com.chatconnect.repository.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    final UserRepo userRepo;
    public UserService(UserRepo userRepo){
        this.userRepo=userRepo;
    }

    public UserResponseDTO usercreation(UserRequestDTO userRequestDTO){
        User user=new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
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
        return null;
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
