package com.javadev.jwtUserApi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Authority  implements GrantedAuthority {


    private long id;
    private String roleCode;
    private String roleDescription;



    @Override
    public String getAuthority() {
        return this.roleCode;
    }
}
