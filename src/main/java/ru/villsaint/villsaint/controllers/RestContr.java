package ru.villsaint.villsaint.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.villsaint.villsaint.models.User;
import ru.villsaint.villsaint.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin/rest")
public class RestContr {

    private final UserService userService;

    public RestContr(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/allusers")
    public ResponseEntity<List<User>> allUsers() {
        List<User> list = userService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @PostMapping(value = "/newUser")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return new ResponseEntity<>("User save",HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("User not save", HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/edit")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try{
            userService.saveUser(user);
            return new ResponseEntity<>("User update", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("User not update", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        try{
            userService.deleteUser(userService.findById(id));
            return new ResponseEntity<>("User delete", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("User not delete", HttpStatus.NOT_FOUND);
        }
    }
}
