package br.com.fiap.epictaskapi.controller;

import java.util.List;
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

import br.com.fiap.epictaskapi.model.ParkingSpace;
import br.com.fiap.epictaskapi.service.ParkingSpaceService;

@RestController
@RequestMapping("/api/parkingspace")
public class ParkingSpaceController {
    @Autowired
    private ParkingSpaceService service;
    
    @GetMapping()
    public Page<ParkingSpace> index(@PageableDefault(size = 10) Pageable paginacao){
        return service.listAll(paginacao);
    }

    @GetMapping("/empty")
    public List<ParkingSpace> showEmptySpaces(){
        return service.listEmptySpaces();
    }

    @PostMapping()
    public ResponseEntity<ParkingSpace> create(@RequestBody @Valid ParkingSpace parkingSpace){
        service.save(parkingSpace);
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(parkingSpace);
    }

    @GetMapping("{id}")
    public ResponseEntity<ParkingSpace> show(@PathVariable Long id){
        return ResponseEntity.of(service.getById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> destroy(@PathVariable Long id){
        Optional<ParkingSpace> optional = service.getById(id);

        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PutMapping("{id}")
    public ResponseEntity<ParkingSpace> update (@PathVariable Long id, @RequestBody @Valid ParkingSpace newParkingSpace){
        Optional<ParkingSpace> optional = service.getById(id);
        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        ParkingSpace parkingSpace = optional.get();
        newParkingSpace.setId(id);
        BeanUtils.copyProperties(newParkingSpace, parkingSpace);

        service.save(parkingSpace);

        return ResponseEntity.ok(parkingSpace);
    }
}
