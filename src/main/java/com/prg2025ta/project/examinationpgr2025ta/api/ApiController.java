package com.prg2025ta.project.examinationpgr2025ta.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ApiController {

    public static void main(String[] args) {
        SpringApplication.run(ApiController.class, args);
    }

    @GetMapping("/")
    public String index() {
        return "<h1>Index</h1>";
    }
}
