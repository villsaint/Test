package ru.villsaint.villsaint.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.villsaint.villsaint.models.User;
import ru.villsaint.villsaint.service.RoleService;
import ru.villsaint.villsaint.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles",roleService.findAll());
        return "admin/admin";
    }

    @PostMapping
    public String create(@ModelAttribute("newUser") @Valid User user,
                         @RequestParam(value = "new_roles", required = false) String[] role,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/admin";
        }
        roleService.setRoles(user, role);
        userService.saveUser(user);
        return "redirect:/admin";
    }

//    @GetMapping("/{id}")
//    public String show(@PathVariable("id") int id, Model model) {
//        model.addAttribute("user", userService.findById(id));
//        return "admin/admin";
//    }

    @PostMapping(value = "/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "edit_roles", required = false) String[] role) {
        if(role == null){
            System.out.println("Роли пустые");
        } else {
            for (String a : role) {
                System.out.println(a);
            }
        }
        roleService.setRoles(user,role);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUser(userService.findById(id));
        return "redirect:/admin";
    }
}

//    @GetMapping("/edit/{id}")
//    public String edit(Model model, @PathVariable("id") int id) {
//        model.addAttribute("userE", userService.findById(id));
//        return "admin/indexTest";
//    }
//
//
//    @PostMapping("/edit/{id}")
//    public String update(@ModelAttribute("userEdit") User user,
//                         @RequestParam(value = "role", required = false) String[] role
////            , BindingResult bindingResult
//    ) {
////        if (bindingResult.hasErrors()) {
////            return "admin/editTest";
////        }
////        model.addAttribute("editUser", userService.findById(id));
//        roleService.setRoles(user, role);
//        userService.saveUser(user);
//        return "redirect:/admin";
//    }

//    @GetMapping("/update/{id}")
//    public String edit(Model model, @PathVariable("id") int id) {
//        model.addAttribute("user", userService.findById(id));
//        return "admin/editTest";
//    }

//    @PatchMapping("/update/{id}")
//    public String update(@ModelAttribute("user") User user,
//                         @RequestParam(value = "role", required = false) String[] role,
////                         BindingResult bindingResult
//    ) {
////        if (bindingResult.hasErrors()) {
////            return "admin/editTest";
////        }
//        roleService.setRoles(user, role);
//        userService.saveUser(user);
//        return "redirect:/admin";
//    }
//    @GetMapping("/{id}/roles")
//    public String showUserRoles(@PathVariable("id") int id, Model model) {
//        model.addAttribute("user", userService.findById(id));
//        return "admin/showRolesTest";
//    }

//    @GetMapping("/new")
//    public String newPerson(@ModelAttribute("user") User person) {
//        return "admin/newTest";
//    }

//    @PostMapping("/new")
//    public String create(@ModelAttribute("user") @Valid User user,
//                         @RequestParam(value = "role", required = false) String[] role,
//                         BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "admin/newTest";
//        }
//        roleService.setRoles(user,role);
//        userService.saveUser(user);
//        return "redirect:/admin";
//    }


//    @GetMapping("/update/{id}")
//    public String edit(Model model, @PathVariable("id") int id) {
//        model.addAttribute("user", userService.findById(id));
//        return "admin/editTest";
//    }
//
//    @PostMapping("/update/{id}")
//    public String update(@ModelAttribute("user") @Valid User user,
//                         @RequestParam(value = "role", required = false) String[] role,
//                         BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "admin/editTest";
//        }
//        roleService.setRoles(user, role);
//        userService.saveUser(user);
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/{id}/delete")
//    public String delete(@PathVariable("id") int id) {
//        userService.deleteUser(userService.findById(id));
//        return "redirect:/admin";
//    }
//}
