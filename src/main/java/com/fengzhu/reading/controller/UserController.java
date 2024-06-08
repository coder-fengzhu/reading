package com.fengzhu.reading.controller;


import com.fengzhu.reading.CommonResponse;
import com.fengzhu.reading.dto.UserDTO;
import com.fengzhu.reading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public CommonResponse<Long> registryUser(@RequestBody UserDTO userDTO) {
        return CommonResponse.newSuccess(userService.registerUser(userDTO));
    }

    @PostMapping("/user/login")
    public CommonResponse<String> login(@RequestParam String userName, @RequestParam String password) {
        return CommonResponse.newSuccess(userService.login(userName, password));
    }
}

