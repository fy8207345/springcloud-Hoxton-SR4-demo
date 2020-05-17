package com.fy.springcloudstreamrabbitmq;

import com.fy.springcloudstreamrabbitmq.controller.RabbitController;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Date;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Slf4j
@SpringBootApplication
public class SpringCloudStreamRabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamRabbitmqApplication.class, args);
    }

    @Autowired
    private EmitterProcessor<RabbitController.Person> emitterProcessor;

    @Bean
    public EmitterProcessor<RabbitController.Person> emitterProcessor(){
        return EmitterProcessor.create();
    }



//    @Bean
//    public Supplier<Flux<RabbitController.Person>> polledSource(){
//        return () -> Flux.fromStream(Stream.generate(new Supplier<RabbitController.Person>() {
//            @Override
//            public RabbitController.Person get() {
//                try {
//                    Thread.sleep(3000);
//                    return new RabbitController.Person(UUID.randomUUID().toString());
//                }catch (Exception e){
//                    e.printStackTrace();
//                    return new RabbitController.Person("Error :" + e.getMessage());
//                }
//            }
//        })).subscribeOn(Schedulers.elastic()).share();
//    }

    @Bean
    public Supplier<Flux<RabbitController.Person>> reaciveSource(){
        return () -> emitterProcessor;
    }

    @Bean
    public Consumer<RabbitController.Person> personConsumer1(){
        return person -> log.info("personConsumer1: received person : {}", person);
    }

    @Bean
    public Function<RabbitController.Person, RabbitController.Person> uppercase(){
        return person -> {
            person.setName(person.getName().toUpperCase());
            return person;
        };
    }

    @Bean
    public Function<Flux<RabbitController.Person>, Flux<RabbitController.Person>> reactiveUppercase(){
        return personFlux -> personFlux.map(person -> {
            person.setName(person.getName().toUpperCase());
            return person;
        });
    }
//
//    @Bean
//    public Function<Flux<RabbitController.Person>, Flux<Void>> reactiveConsumer(){
//        return personFlux -> personFlux.map(System.out::println).filter()
//    }
}
