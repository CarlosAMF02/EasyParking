package br.com.fiap.epictaskapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.fiap.epictaskapi.model.ParkingLot;
import br.com.fiap.epictaskapi.model.ParkingSpace;
import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.repository.ParkingSpaceRepository;
import br.com.fiap.epictaskapi.repository.UserRepository;

@Service
public class ParkingSpaceService {
    @Autowired
    ParkingSpaceRepository parkingSpaceRepository;

    @Autowired
    UserRepository userRepository;

    public Page<ParkingSpace> listAll(Pageable paginacao){
        return parkingSpaceRepository.findAll(paginacao);
    }

    public List<ParkingSpace> listAll() {
        return parkingSpaceRepository.findAll();
    }

    public List<ParkingSpace> listByParkingLot(ParkingLot parkingLot) {
        return parkingSpaceRepository.findByParkingLot(parkingLot);
    }

    public List<ParkingSpace> listEmptySpaces() {
        return parkingSpaceRepository.findByCar(null);
    }

    public void save(ParkingSpace parkingSpace) {
        User user = userRepository.findById(parkingSpace.getParkingLot().getUser().getId()).orElse(null);
        ParkingSpace existingSpace = null;
        if (parkingSpace.getId() != null) {
            existingSpace = getById(parkingSpace.getId()).orElse(null);
        }
        System.out.println(user);
        if (existingSpace != null) {
            if (parkingSpace.getIsEmpty() && !existingSpace.getIsEmpty()) {
                user.setMySpaces(user.getMySpaces() + 1);
            } else {
                if (!parkingSpace.getIsEmpty() && existingSpace.getIsEmpty()) {
                    user.setMySpaces(user.getMySpaces() - 1);
                }
            }
        } else {
            if (parkingSpace.getIsEmpty()) {
                user.setMySpaces(user.getMySpaces() + 1);
            }
        }

        userRepository.save(user);
        parkingSpaceRepository.save(parkingSpace);
    }

    public Optional<ParkingSpace> getById(Long id) {
        return parkingSpaceRepository.findById(id);
    }

    public void deleteById(Long id) {
        parkingSpaceRepository.deleteById(id);
    }
}
