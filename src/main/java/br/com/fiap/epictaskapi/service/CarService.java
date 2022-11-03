package br.com.fiap.epictaskapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.fiap.epictaskapi.model.Car;
import br.com.fiap.epictaskapi.repository.CarRepository;

@Service
public class CarService {
    @Autowired
    CarRepository carRepository;

    public Page<Car> listAll(Pageable paginacao){
        return carRepository.findAll(paginacao);
    }

    public List<Car> listAll() {
        return carRepository.findAll();
    }

    public void save(Car car) {
        carRepository.save(car);
    }

    public Optional<Car> getById(Long id) {
        return carRepository.findById(id);
    }

    public Optional<Car> getByLicensePlate(String licensePlate) {
        return carRepository.findByLicensePlate(licensePlate);
    }

    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }
}
