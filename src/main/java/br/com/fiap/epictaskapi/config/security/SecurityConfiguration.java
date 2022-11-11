package br.com.fiap.epictaskapi.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http    
            .httpBasic()
            .and()
            .authorizeHttpRequests()
                .antMatchers(HttpMethod.GET, "/user").authenticated()
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .antMatchers(HttpMethod.GET, "/user/delete/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/parkinglot").authenticated()
                .antMatchers(HttpMethod.POST, "/parkinglot").permitAll()
                .antMatchers(HttpMethod.GET, "/parkinglot/delete/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/parkingspace").authenticated()
                .antMatchers(HttpMethod.POST, "/parkingspace").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/parkingspace/delete/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/car").authenticated()
                .antMatchers(HttpMethod.POST, "/car").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/car/delete/**").hasRole("ADMIN")
                
                .anyRequest().permitAll()
            .and()
                .formLogin()

            .and() 
                .csrf().disable()

        ;        
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
    
}
