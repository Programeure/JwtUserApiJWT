package com.javadev.jwtUserApi;

import com.javadev.jwtUserApi.entities.Authority;
import com.javadev.jwtUserApi.entities.User;
import com.javadev.jwtUserApi.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.lang.UsesSunMisc;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class JwtUserApiApplication {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    public static void main(String[] args) {
        SpringApplication.run(JwtUserApiApplication.class, args);
    }

    @PostConstruct
    public void init(){
        java.util.List<Authority> authorities= new java.util.ArrayList<>();
        authorities.add(createAuthority("USER","user role"));
        authorities.add(createAuthority("ADMIN","admin role"));

        User user=new User();
        user.setId(1);
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("123"));
        user.setFirstname("Wadson");
        user.setLastname("Dutervil");
        user.setPhone("31340028");
        user.setAuthorities(authorities);
        //userRepository.save(user);

    }

    private Authority createAuthority(String roleCode, String roleDecription){
        Authority authority =new Authority();
        authority.setRoleCode(roleCode);
        authority.setRoleDescription(roleDecription);
        return authority;
    }
}
