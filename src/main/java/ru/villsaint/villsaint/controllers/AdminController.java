package ru.villsaint.villsaint.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.villsaint.villsaint.models.Role;
import ru.villsaint.villsaint.models.User;
import ru.villsaint.villsaint.service.UserService;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("user", userService.findAll());
//        return "admin/index";
        return "admin/indexTest";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/showTest";
    }

    @GetMapping("/{id}/roles")
    public String showUserRoles(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/showRolesTest";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("user") User person) {
        return "admin/newTest";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "admin/newTest";

        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/update/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.findById(id));
        return "admin/editTest";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "admin/editTest";
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUser(userService.findById(id));
        return "redirect:/admin";
    }
}
