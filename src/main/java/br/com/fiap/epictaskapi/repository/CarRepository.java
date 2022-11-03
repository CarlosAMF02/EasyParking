package br.com.fiap.epictaskapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.epictaskapi.model.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
        Optional<Car> findByLicensePlate(String licensePlate);
}
