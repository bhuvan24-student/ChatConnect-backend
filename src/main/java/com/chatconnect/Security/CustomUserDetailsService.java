package com.chatconnect.Security;

import com.chatconnect.Users.User;
import com.chatconnect.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {

     UserRepo userRepo;
     public CustomUserDetailsService(UserRepo userRepo){
         this.userRepo=userRepo;
     }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         Optional<User> user=userRepo.findByEmail(email);
         if(!user.isPresent()){
             throw  new UsernameNotFoundException("User not found");
         }
        return null;
    }
}
