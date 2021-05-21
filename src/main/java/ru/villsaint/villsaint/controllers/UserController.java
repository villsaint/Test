package ru.villsaint.villsaint.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.villsaint.villsaint.models.User;
import ru.villsaint.villsaint.repo.UserRepo;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepo userRepo;

    @Autowired
    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping()
    public String show(Model model, Authentication authentication) {
        User user = userRepo.findUserByUsername(authentication.getName());
        model.addAttribute("user", user);
        return "user/userTest";
    }
}
