package br.com.fiap.epictaskapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.epictaskapi.model.Car;
import br.com.fiap.epictaskapi.model.ParkingLot;
import br.com.fiap.epictaskapi.model.ParkingSpace;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {
    List<ParkingSpace> findByCar(Car car);

    List<ParkingSpace> findByParkingLot(ParkingLot parkingLot);
}
