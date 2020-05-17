package com.fy.springcloudstreamrabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RabbitController {

    @GetMapping("/person/{name}")
    public String person(@PathVariable String name){
        return name;
    }
}
