package com.maple.harbor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AEchoController {

    @GetMapping("echo/{echo}")
    public String echo(@PathVariable String echo){
        return echo;
    }

}
