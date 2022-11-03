package br.com.fiap.epictaskapi.controller.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.epictaskapi.model.Car;
import br.com.fiap.epictaskapi.model.ParkingLot;
import br.com.fiap.epictaskapi.model.ParkingSpace;
import br.com.fiap.epictaskapi.service.CarService;
import br.com.fiap.epictaskapi.service.ParkingLotService;
import br.com.fiap.epictaskapi.service.ParkingSpaceService;

@Controller
@RequestMapping("/parkingspace")
public class ParkingSpaceWebController {
    @Autowired
    ParkingSpaceService service;

    @Autowired
    CarService carService;

    @Autowired
    ParkingLotService parkingLotService;

    @GetMapping("{idLot}")
    public ModelAndView index(@PathVariable(name = "idLot") Long idLot){
        ParkingLot parkingLot = parkingLotService.getById(idLot).orElse(null);
        if (parkingLot == null) return new ModelAndView("parkinglot/index").addObject("parkingLots", service.listAll());

        return new ModelAndView("parkingspace/index").addObject("spaces", service.listByParkingLot(parkingLot)).addObject("parkingLot", parkingLot);
    }

    @GetMapping("/new")
    public String form(ParkingSpace parkingSpace){
        return "parkingspace/new";
    }

    @PostMapping()
    public String create(
            @Valid ParkingSpace parkingSpace, 
            BindingResult binding, 
            RedirectAttributes redirect
        ){

        if (binding.hasErrors()) return "/parkinglot/new";
        String message = (parkingSpace.getId() == null)
                ?"Vaga cadastrada com sucesso"
                :"Vaga alterada com sucesso";
        Car car = carService.getByLicensePlate(parkingSpace.getCar().getLicensePlate()).orElse(null);
        ParkingLot parkingLot = parkingLotService.getByName(parkingSpace.getParkingLot().getName()).orElse(null);
        if (car == null) {
            parkingSpace.setEmpty(true);
        } else {
            parkingSpace.setEmpty(false);
        }
        if (parkingLot == null) {
            return "/parkingspace/new";
        }
        parkingSpace.setParkingLot(parkingLot);
        parkingSpace.setCar(car);
        service.save(parkingSpace);
        redirect.addFlashAttribute("message", message);
        return "redirect:/parkinglot";
    
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id, RedirectAttributes redirect){
        service.deleteById(id);
        redirect.addFlashAttribute("message", "Vaga apagada com sucesso");
        return "redirect:/parkinglot";
    }

    @GetMapping("/edit/{idLot}/{id}")
    public ModelAndView edit(@PathVariable(name = "idLot") Long idLot, @PathVariable(name = "id") Long id){
        Optional<ParkingSpace> optional = service.getById(id);
        Optional<ParkingLot> optionalLot = parkingLotService.getById(idLot);
        if (optional.isEmpty()) return new ModelAndView("parkinglot/index").addObject("parkingLots", service.listAll());
        
        return new ModelAndView("parkingspace/new")
            .addObject("parkingSpace", optional.get())
            .addObject("parkingLot", optionalLot.get());
    }
}
