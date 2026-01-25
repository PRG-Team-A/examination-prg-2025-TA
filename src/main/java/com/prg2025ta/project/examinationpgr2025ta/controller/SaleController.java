package com.prg2025ta.project.examinationpgr2025ta.controller;

import com.prg2025ta.project.examinationpgr2025ta.dto.SaleRequest;
import com.prg2025ta.project.examinationpgr2025ta.model.Sale;
import com.prg2025ta.project.examinationpgr2025ta.service.SupermarketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SupermarketService supermarketService;

    @Autowired
    public SaleController(SupermarketService supermarketService) {
        this.supermarketService = supermarketService;
    }

    @PostMapping
    public ResponseEntity<Sale> executeSale(@Valid @RequestBody SaleRequest saleRequest) {
        Sale sale = supermarketService.processSale(saleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(sale);
    }
}
