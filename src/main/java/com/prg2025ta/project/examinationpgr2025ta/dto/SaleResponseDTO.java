package com.prg2025ta.project.examinationpgr2025ta.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SaleResponseDTO {
    private Long id;
    private LocalDate date;
    private Double total;
    private String employeeUsername;
    private String paymentMethod;

    public SaleResponseDTO(Long id, LocalDate date, Double total, String employeeUsername, String paymentMethod) {
        this.id = id;
        this.date = date;
        this.total = total;
        this.employeeUsername = employeeUsername;
        this.paymentMethod = paymentMethod;
    }
}
