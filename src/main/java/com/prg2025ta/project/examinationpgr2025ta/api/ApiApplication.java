package com.prg2025ta.project.examinationpgr2025ta.api;

import com.prg2025ta.project.examinationpgr2025ta.Warehouse;
import com.prg2025ta.project.examinationpgr2025ta.database.DatabaseOperations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {
    public static Warehouse warehouse;

    public static void main(String[] args) {
        warehouse = new Warehouse();
        SpringApplication.run(ApiApplication.class, args);
    }
}
