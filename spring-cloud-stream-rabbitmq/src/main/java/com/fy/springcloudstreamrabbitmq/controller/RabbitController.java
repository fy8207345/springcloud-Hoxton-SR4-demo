package com.fy.springcloudstreamrabbitmq.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.EmitterProcessor;

import java.util.UUID;
import java.util.function.Supplier;

@Slf4j
@RestController
public class RabbitController {

    @Autowired
    private StreamBridge bridge;

    @Autowired
    private EmitterProcessor<Person> emitterProcessor;

    @GetMapping("/person")
    public String person(){
        String id = UUID.randomUUID().toString();
//        bridge.send("topic1", new Person(id));
        emitterProcessor.onNext(new Person(id));
        return id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Person{
        private String name;
    }
}
