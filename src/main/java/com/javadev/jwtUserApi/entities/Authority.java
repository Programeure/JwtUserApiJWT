package com.javadev.jwtUserApi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="AUTH_AUTORITY")
@Entity
public class Authority  implements GrantedAuthority {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String roleCode;
    private String roleDescription;



    @Override
    public String getAuthority() {
        return this.roleCode;
    }
}
