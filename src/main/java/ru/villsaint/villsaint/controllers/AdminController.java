package ru.villsaint.villsaint.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.villsaint.villsaint.models.User;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String index() {
        return "admin/admin";
    }

    @PatchMapping(value = "/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "edit_roles", required = false) String[] role,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/admin";
        }
        return "redirect:/admin";
    }
}

