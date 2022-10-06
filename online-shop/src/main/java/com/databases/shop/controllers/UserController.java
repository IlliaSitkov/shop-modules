package com.databases.shop.controllers;

import com.databases.shop.mapstruct.dtos.user.UserGetDto;
import com.databases.shop.services.interfaces.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@Validated
@RequestMapping("/users")
public class UserController {


    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/email")
    public UserGetDto getUserByEmail(@RequestParam String email) {
        return userService.findUserByEmail(email);
    }









}






