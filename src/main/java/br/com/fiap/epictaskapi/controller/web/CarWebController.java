package br.com.fiap.epictaskapi.controller.web;

import java.util.Optional;

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
import br.com.fiap.epictaskapi.service.CarService;

@Controller
@RequestMapping("/car")
public class CarWebController {

    @Autowired
    CarService carService;

    @GetMapping
    public ModelAndView index(){
        return new ModelAndView("car/index").addObject("cars", carService.listAll());
    }

    @GetMapping("new")
    public String form(Car car){
        return "car/new";
    }

    @PostMapping
    public String create(
            Car car, 
            BindingResult binding, 
            RedirectAttributes redirect
        ){

        if (binding.hasErrors()) return "/user/new";
        String message = (car.getId() == null)
                ?"Carro cadastrado com sucesso"
                :"Carro alterado com sucesso";

        carService.save(car);
        redirect.addFlashAttribute("message", message);
        return "redirect:/car";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        carService.deleteById(id);
        redirect.addFlashAttribute("message", "Carro apagado com sucesso");
        return "redirect:/car";
    }

    @GetMapping("{id}")
    public ModelAndView edit(@PathVariable Long id){
        Optional<Car> optional = carService.getById(id);
        if (optional.isEmpty()) return new ModelAndView("car/index");

        return new ModelAndView("car/new")
            .addObject("car", optional.get());
    }
    
}
