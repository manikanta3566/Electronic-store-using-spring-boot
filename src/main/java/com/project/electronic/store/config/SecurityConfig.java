package com.project.electronic.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    //password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //inmemory user details stored
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user1=User.builder()
                .username("test")
                .password(passwordEncoder().encode("password1"))
                .roles("USER")
                .build();
        UserDetails user2=User.builder()
                .username("demo")
                .password(passwordEncoder().encode("password2"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1,user2);
    }
}
