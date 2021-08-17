package com.javadev.jwtUserApi.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;
    private  JWTToknHelper jwtToknHelper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authToken=this.jwtToknHelper.getToken(request);
        if (null !=authToken){
            String username=jwtToknHelper.getUsernameFromToken(authToken);
            System.out.println("The Username est : "+username);
            if(null!=username){
                UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
                System.out.println("The password from USerDetails est : "+userDetails.getPassword());
               if (jwtToknHelper.validateToken(authToken,userDetails)){
                   System.out.println("OK");
                   UsernamePasswordAuthenticationToken
                           usernamePasswordAuthenticationToken
                           =new UsernamePasswordAuthenticationToken(userDetails,null);
                   usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));
                   SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }

        }
        filterChain.doFilter( request,response );

    }

}
