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

import br.com.fiap.epictaskapi.model.ParkingLot;
import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.service.ParkingLotService;
import br.com.fiap.epictaskapi.service.UserService;

@Controller
@RequestMapping("/parkinglot")
public class ParkingLotWebController {
    @Autowired
    ParkingLotService service;

    @Autowired
    UserService userService;

    @GetMapping
    public ModelAndView index(){
        return new ModelAndView("parkinglot/index").addObject("parkingLots", service.listAll());
    }

    @GetMapping("new")
    public String form(ParkingLot parkingLot){
        return "parkinglot/new";
    }

    @PostMapping
    public String create(
            @Valid ParkingLot parkingLot, 
            BindingResult binding, 
            RedirectAttributes redirect
        ){

        if (binding.hasErrors()) return "/parkinglot/new";
        String message = (parkingLot.getId() == null)
                ?"Estacionamento cadastrado com sucesso"
                :"Estacionamento alterado com sucesso";
        User user = userService.getByEmail("admin@fiap.com.br").orElse(null);
        if (user != null) {
            parkingLot.setUser(user);
            service.save(parkingLot);
            redirect.addFlashAttribute("message", message);
            return "redirect:/parkinglot";
        } else {
            return "/parkinglot/new";
        }
    
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        service.deleteById(id);
        redirect.addFlashAttribute("message", "Estacionamento apagado com sucesso");
        return "redirect:/parkinglot";
    }

    @GetMapping("{id}")
    public ModelAndView edit(@PathVariable Long id){
        Optional<ParkingLot> optional = service.getById(id);
        if (optional.isEmpty()) return new ModelAndView("parkinglot/index");
        
        return new ModelAndView("parkinglot/new")
            .addObject("parkingLot", optional.get());
    }
}
