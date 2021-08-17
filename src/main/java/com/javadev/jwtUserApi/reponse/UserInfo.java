package com.javadev.jwtUserApi.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInfo {
    private String firstname;
    private String lastname;
    private String username;
    private  Object roles;

}
