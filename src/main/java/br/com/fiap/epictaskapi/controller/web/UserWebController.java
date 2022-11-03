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

import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.service.UserService;

@Controller
@RequestMapping("/user")
public class UserWebController {

    @Autowired
    UserService userService;

    @GetMapping
    public ModelAndView index(){
        return new ModelAndView("user/index").addObject("users", userService.listAll());
    }

    @GetMapping("new")
    public String form(User user){
        return "user/new";
    }

    @PostMapping
    public String create(
            User user, 
            BindingResult binding, 
            RedirectAttributes redirect
        ){

        if (binding.hasErrors()) return "/user/new";
        System.out.println(user.getId());
        String message = (user.getId() == null)
                ?"Usuário cadastrado com sucesso"
                :"Usuário alterado com sucesso";

        userService.save(user);
        redirect.addFlashAttribute("message", message);
        return "redirect:/user";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        userService.deleteById(id);
        redirect.addFlashAttribute("message", "Usuário apagado com sucesso");
        return "redirect:/task";
    }

    @GetMapping("{id}")
    public ModelAndView edit(@PathVariable Long id){
        Optional<User> optional = userService.getById(id);
        if (optional.isEmpty()) return new ModelAndView("user/index");

        return new ModelAndView("user/new")
            .addObject("user", optional.get());
    }
    
}
