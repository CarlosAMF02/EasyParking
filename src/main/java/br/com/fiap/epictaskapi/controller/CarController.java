package br.com.fiap.epictaskapi.controller;

import java.util.Optional;

import javax.validation.Valid;

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

import br.com.fiap.epictaskapi.model.Car;
import br.com.fiap.epictaskapi.service.CarService;

@RestController
@RequestMapping("/api/car")
public class CarController {
    @Autowired
    private CarService service;
    
    @GetMapping()
    public Page<Car> index(@PageableDefault(size = 6) Pageable paginacao){
        return service.listAll(paginacao);
    }

    @PostMapping()
    public ResponseEntity<Car> create(@RequestBody @Valid Car car){
        service.save(car);
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(car);
    }

    @GetMapping("{id}")
    public ResponseEntity<Car> show(@PathVariable Long id){
        return ResponseEntity.of(service.getById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> destroy(@PathVariable Long id){
        Optional<Car> optional = service.getById(id);

        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PutMapping("{id}")
    public ResponseEntity<Car> update (@PathVariable Long id, @RequestBody @Valid Car newCar){
        Optional<Car> optional = service.getById(id);
        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        Car car = optional.get();
        newCar.setId(id);
        BeanUtils.copyProperties(newCar, car);

        service.save(car);

        return ResponseEntity.ok(car);
    }
}
