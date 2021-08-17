package com.javadev.jwtUserApi.controller;


import com.javadev.jwtUserApi.config.JWTToknHelper;
import com.javadev.jwtUserApi.entities.User;
import com.javadev.jwtUserApi.reponse.AuthenticationResponse;
import com.javadev.jwtUserApi.reponse.UserInfo;
import com.javadev.jwtUserApi.request.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class AuthentiationController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JWTToknHelper jwtToknHelper;

    @Autowired
    UserDetailsService userDetailsService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody  AuthenticationRequest authenticationRequest ) throws InvalidKeySpecException, NoSuchAlgorithmException {
        final Authentication authentication=authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken
                        (authenticationRequest.getUsername(),authenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user=(User)authentication.getPrincipal();
        String jwtToken=jwtToknHelper.generateToken(user.getUsername());
        AuthenticationResponse response=new AuthenticationResponse();
        response.setToken(jwtToken);
        System.out.println("Login Is Call");
        return  ResponseEntity.ok(response);

    }

    @GetMapping("/auth/userInfo")
    public ResponseEntity<?>getUsers(Principal user){
        User userObject=(User)userDetailsService.loadUserByUsername(user.getName());

        UserInfo userInfo=new UserInfo();
        userInfo.setFirstname(userObject.getFirstname());
        userInfo.setLastname(userObject.getLastname());
        userInfo.setUsername(userObject.getUsername());
        userInfo.setRoles(userObject.getAuthorities().toArray());
        System.out.println("ALl user Request Is Called"+userObject.getUsername());
        System.out.println(userInfo.toString());
        return ResponseEntity.ok(userInfo);


    }


}
