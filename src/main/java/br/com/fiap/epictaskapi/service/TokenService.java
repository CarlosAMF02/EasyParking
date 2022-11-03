package br.com.fiap.epictaskapi.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.fiap.epictaskapi.model.User;

@Service
public class TokenService {

    public String createToken(User user){
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 60_000);
        
        return JWT.create()
            .withSubject(user.getEmail())
            .withIssuedAt(issuedAt)
            .withExpiresAt(expiresAt)
            .sign(Algorithm.HMAC512("secret")
        );
    }

    public boolean valide(String header) {
        // "Basic kadfkjhdflghsdkfugkslduhfgk"

        if (header == null || !header.startsWith("Bearer ") || header.isEmpty() ){
            return false;
        }

        String token = header.substring(7);

        try{
            JWT
                .require(Algorithm.HMAC512("secret"))
                .build()
                .verify(token);
            return true;

        }catch(Exception e){
            return false;
        }

    }

}
