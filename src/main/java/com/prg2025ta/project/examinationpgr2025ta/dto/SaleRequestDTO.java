package com.prg2025ta.project.examinationpgr2025ta.dto;

import com.prg2025ta.project.examinationpgr2025ta.model.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SaleRequestDTO {
    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Product list is required")
    private List<ProductQuantityDTO> products;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    public SaleRequestDTO(Long employeeId, List<ProductQuantityDTO> products, PaymentMethod paymentMethod) {
        this.employeeId = employeeId;
        this.products = products;
        this.paymentMethod = paymentMethod;
    }
}
