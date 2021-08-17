package com.javadev.jwtUserApi.config;

import com.javadev.jwtUserApi.service.CostumUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JWTToknHelper jwtToknHelper;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private CostumUserService costumUserService;
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // IN MEMORY AUTH
        /* auth.inMemoryAuthentication()
                 .withUser("admin")
                 .password(passwordEncoder().encode("123"))
                 .authorities("USER","ADMIN");*/
        // JDBC AUTH
       auth.userDetailsService(costumUserService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      //  http.authorizeRequests().anyRequest().authenticated();
      //  http.authorizeRequests((request) -> request.antMatchers("/h2-console/**").permitAll());
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling().authenticationEntryPoint( restAuthenticationEntryPoint).and()
                .authorizeRequests((request) -> request.antMatchers("/h2-console/**","/api/v1/auth/login").permitAll().antMatchers(HttpMethod.OPTIONS,"/**").permitAll().anyRequest().authenticated())
                .addFilterBefore(new JWTAuthenticationFilter(costumUserService,jwtToknHelper), UsernamePasswordAuthenticationFilter.class);


        //h2-console
       http.cors();
       http.csrf().disable().headers().frameOptions().disable();

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
