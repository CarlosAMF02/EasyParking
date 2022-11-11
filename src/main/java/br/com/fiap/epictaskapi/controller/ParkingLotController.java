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

import br.com.fiap.epictaskapi.controller.DTO.ParkingLotDTO;
import br.com.fiap.epictaskapi.model.ParkingLot;
import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.service.ParkingLotService;
import br.com.fiap.epictaskapi.service.UserService;

@RestController
@RequestMapping("/api/parkinglot")
public class ParkingLotController {
    @Autowired
    private ParkingLotService service;

    @Autowired
    private UserService userService;

    @GetMapping()
    public Page<ParkingLot> index(@PageableDefault(size = 4) Pageable paginacao){
        return service.listAll(paginacao);
    }

    @PostMapping()
    public ResponseEntity<ParkingLot> create(@RequestBody @Valid ParkingLotDTO parkingLotDTO){
        User user = userService.getById(parkingLotDTO.getUserId()).orElse(null);
        if(user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(parkingLotDTO.getName());
        parkingLot.setAddress(parkingLotDTO.getAddress());
        parkingLot.setUser(user);

        service.save(parkingLot);
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(parkingLot);
    }

    @GetMapping("{id}")
    public ResponseEntity<ParkingLot> show(@PathVariable Long id){
        return ResponseEntity.of(service.getById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> destroy(@PathVariable Long id){
        Optional<ParkingLot> optional = service.getById(id);

        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PutMapping("{id}")
    public ResponseEntity<ParkingLot> update (@PathVariable Long id, @RequestBody @Valid ParkingLotDTO parkingLotDTO){
        Optional<ParkingLot> optional = service.getById(id);
        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        User user = userService.getById(parkingLotDTO.getUserId()).orElse(null);
        if(user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        ParkingLot parkingLot = optional.get();
        parkingLot.setUser(user);
        parkingLot.setName(parkingLotDTO.getName());
        parkingLot.setAddress(parkingLotDTO.getAddress());

        service.save(parkingLot);

        return ResponseEntity.ok(parkingLot);
    }
}
