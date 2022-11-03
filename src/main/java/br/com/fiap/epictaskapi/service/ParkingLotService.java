package br.com.fiap.epictaskapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.fiap.epictaskapi.model.ParkingLot;
import br.com.fiap.epictaskapi.repository.ParkingLotRepository;

@Service
public class ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository;

    public Page<ParkingLot> listAll(Pageable paginacao){
        return parkingLotRepository.findAll(paginacao);
    }

    public List<ParkingLot> listAll() {
        return parkingLotRepository.findAll();
    }

    public void save(ParkingLot parkingLot) {
        parkingLotRepository.save(parkingLot);
    }

    public Optional<ParkingLot> getById(Long id) {
        return parkingLotRepository.findById(id);
    }

    public Optional<ParkingLot> getByName(String name) {
        return parkingLotRepository.findByName(name);
    }

    public void deleteById(Long id) {
        parkingLotRepository.deleteById(id);
    }
}
