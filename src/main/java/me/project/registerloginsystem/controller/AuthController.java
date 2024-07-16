package me.project.registerloginsystem.controller;

import me.project.registerloginsystem.entity.Users;
import me.project.registerloginsystem.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/")
public class AuthController {

    @Autowired
    private CustomUserDetailsService service;

    @PostMapping("register")
    public ResponseEntity<Users> register(@RequestBody Users users) {
        return new ResponseEntity<>(service.save(users), HttpStatus.OK);
    }

    @GetMapping("find-all")
    public ResponseEntity<List<Users>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<Users> update(@RequestBody Users users) {
        return new ResponseEntity<>(service.update(users), HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity delete(@RequestParam int id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
