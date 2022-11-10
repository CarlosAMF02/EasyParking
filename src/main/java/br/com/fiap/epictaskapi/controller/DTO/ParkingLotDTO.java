package br.com.fiap.epictaskapi.controller.DTO;

import javax.validation.constraints.Size;

public class ParkingLotDTO {
    @Size(min = 2, max=50, message = "digite um nome entre 2 e 50 caracteres")
    private String name;

    @Size(min = 10, max=60, message = "digite um endereço entre 10 e 60 caracteres")
    private String address;

    private Long userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    
}
