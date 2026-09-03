package com.chatconnect.Security.Jwt;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         String auth=request.getHeader("Authorization");
         if(auth!=null && auth.startsWith("Bearer ")) {
              String token = auth.substring(7);
             if (jwtService.validatetoken(token)) {
                 String email = jwtService.extractemail(token);
                 UserDetails userDetails=userDetailsService.loadUserByUsername(email);
                 UsernamePasswordAuthenticationToken authentication =
                         new UsernamePasswordAuthenticationToken(
                                 userDetails,null, userDetails.getAuthorities());
                 SecurityContextHolder.getContext().setAuthentication(authentication);
             }
         }
        filterChain.doFilter(request,response);

    }


}
