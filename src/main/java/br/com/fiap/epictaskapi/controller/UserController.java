package br.com.fiap.epictaskapi.controller;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.service.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping()
    public Page<User> index(@PageableDefault(size = 5) Pageable paginacao){
        return userService.listAll(paginacao);
    }

    @PostMapping()
    public ResponseEntity<User> create(@RequestBody User user){

        userService.save(user);
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(user);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> show(@PathVariable Long id){
        return ResponseEntity.of(userService.getById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> destroy(@PathVariable Long id){
        Optional<User> optional = userService.getById(id);

        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PutMapping("{id}")
    public ResponseEntity<User> update (@PathVariable Long id, @RequestBody User newUser){
        Optional<User> optional = userService.getById(id);
        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        User user = optional.get();
        newUser.setId(id);
        BeanUtils.copyProperties(newUser, user);

        userService.save(user);

        return ResponseEntity.ok(user);
    }
    
}
