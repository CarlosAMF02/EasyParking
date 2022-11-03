package br.com.fiap.epictaskapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
public class ParkingSpace {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max=6, message = "digite um nome entre 2 e 6 caracteres")
    private String name;

    @Min(-10) @Max(100)
    private int floor;

    @ManyToOne()
    @JoinColumn(name = "id_estacionamento")
    private ParkingLot parkingLot;

    @OneToOne()
    @JoinColumn(name = "id_carro")
    private Car car;

    private boolean isEmpty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public boolean getIsEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + floor;
        result = prime * result + ((parkingLot == null) ? 0 : parkingLot.hashCode());
        result = prime * result + ((car == null) ? 0 : car.hashCode());
        result = prime * result + (isEmpty ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ParkingSpace other = (ParkingSpace) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (floor != other.floor)
            return false;
        if (parkingLot == null) {
            if (other.parkingLot != null)
                return false;
        } else if (!parkingLot.equals(other.parkingLot))
            return false;
        if (car == null) {
            if (other.car != null)
                return false;
        } else if (!car.equals(other.car))
            return false;
        if (isEmpty != other.isEmpty)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ParkingSpace [id=" + id + ", name=" + name + ", floor=" + floor + ", parkingLot=" + parkingLot
                + ", car=" + car + ", isEmpty=" + isEmpty + "]";
    }
}
