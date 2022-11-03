package br.com.fiap.epictaskapi.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import br.com.fiap.epictaskapi.service.TokenService;

public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if(new TokenService().valide(header)){
            System.out.println("AUTENTICADO");
        }else{
            System.out.println("Não AUTENTICADO");

        }

        
        filterChain.doFilter(request, response);
    }
    


}
