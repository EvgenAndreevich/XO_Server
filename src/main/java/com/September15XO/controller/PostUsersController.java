package com.September15XO.controller;

import com.September15XO.domain.Users;
import com.September15XO.service.UpdateUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/update-users")
public class PostUsersController {
    @Autowired
    private UpdateUsersService updateUsersService;

    @GetMapping("add-new-users")
    public ResponseEntity<Users> addNewUsers(@RequestParam String name) {
        Users users = new Users();
        users.setNamePlayer(name);
        return ResponseEntity.ok(updateUsersService.addNewUsers(users));
    }
}


