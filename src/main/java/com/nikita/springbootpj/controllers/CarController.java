package com.nikita.springbootpj.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/car")
public class CarController {

    @GetMapping
    String getMsg(){
        return "CarController funziona";
    }
}
