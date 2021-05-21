package ru.villsaint.villsaint.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.villsaint.villsaint.models.User;
import ru.villsaint.villsaint.service.RoleService;
import ru.villsaint.villsaint.service.UserService;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin/rest")
public class RestContr {

    private final UserService userService;


    public RestContr(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/allusers")
    public List<User> allUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @PostMapping(value = "/newUser")
    public void saveUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @PutMapping("/edit")
    public void updateUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(userService.findById(id));
    }
}
