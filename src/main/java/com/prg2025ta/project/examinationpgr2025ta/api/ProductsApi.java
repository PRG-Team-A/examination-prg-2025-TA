package com.prg2025ta.project.examinationpgr2025ta.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsApi {
    @GetMapping("/")
    public String index() {
        throw new UnsupportedOperationException();
    }
}
