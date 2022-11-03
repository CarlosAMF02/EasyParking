package br.com.fiap.epictaskapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.service.TokenService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    TokenService service;

    @Autowired
    AuthenticationManager authenticationManager;
    
    @PostMapping
    public ResponseEntity<Object> auth(@RequestBody User user){

        Authentication authentication = 
            new UsernamePasswordAuthenticationToken(user.getUsername(), 
                                                    user.getPassword());
        authenticationManager.authenticate(authentication);

        return ResponseEntity.ok(service.createToken(user));
    }

}
