package br.com.fiap.epictaskapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.epictaskapi.model.Role;
import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.repository.UserRepository;

@Configuration
public class DatabaseSeed implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        User user1 = new User(
            "Joao", 
            "joao@fiap.com.br", 
            "$2a$12$RVc1Cze5T/Ea6BclNRVwQejWSlaXOfBow8KC3MU0eifsyjfOFWKE2",
            new Role("ROLE_USER")
        );

        User user2 = new User(
            "Admin", 
            "admin@fiap.com.br", 
            "$2a$12$RVc1Cze5T/Ea6BclNRVwQejWSlaXOfBow8KC3MU0eifsyjfOFWKE2",
            new Role("ROLE_ADMIN")
        );

        //  userRepository.save(user1);

        //  userRepository.save(user2);
        
    }
    
}
