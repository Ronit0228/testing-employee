package com.learning.testing;

import com.learning.testing.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class TestingApplication {

//    public final DataService dataService;

	public static void main(String[] args) {
		SpringApplication.run(TestingApplication.class, args);
	}

//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("The Data is " + dataService.getData());
//    }
}

// Three ways to run the application from the maven
// 1 - mvn "-Dspring-boot.run.profiles=dev" spring-boot:run
// 2 - $env:SPRING_PROFILES_ACTIVE="prod"
        //mvn spring-boot:run
//3 - run the jar file : java "-Dspring.profiles.active=prod" -jar .\target\testing-0.0.1-SNAPSHOT.jar

