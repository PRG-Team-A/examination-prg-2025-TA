package com.prg2025ta.project.examinationpgr2025ta.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ApiController {

    @Value("${server.port}")

    public static void main(String[] args) {
        SpringApplication.run(ProductsApi.class, args);
    }
}
