package com.prg2025ta.project.examinationpgr2025ta.service;

import com.prg2025ta.project.examinationpgr2025ta.dto.SaleRequest;
import com.prg2025ta.project.examinationpgr2025ta.exceptions.OutOfStockException;
import com.prg2025ta.project.examinationpgr2025ta.model.*;
import com.prg2025ta.project.examinationpgr2025ta.repository.EmployeeRepository;
import com.prg2025ta.project.examinationpgr2025ta.repository.PaymentRepository;
import com.prg2025ta.project.examinationpgr2025ta.repository.ProductRepository;
import com.prg2025ta.project.examinationpgr2025ta.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class SupermarketService {

    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final PaymentRepository paymentRepository;

    @Autowired
    public SupermarketService(EmployeeRepository employeeRepository,
                             ProductRepository productRepository,
                             SaleRepository saleRepository,
                             PaymentRepository paymentRepository) {
        this.employeeRepository = employeeRepository;
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Sale processSale(SaleRequest saleRequest) {
        // 1. Validar que el empleado existe
        Employee employee = employeeRepository.findById(saleRequest.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + saleRequest.getEmployeeId()));

        // 2. Validar stock y calcular total
        double total = 0.0;
        for (SaleRequest.ProductItem productItem : saleRequest.getProducts()) {
            Product product = productRepository.findById(productItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + productItem.getProductId()));

            // Validar stock
            if (product.getStock() < productItem.getQuantity()) {
                throw new OutOfStockException("Insufficient stock for product: " + product.getName() + 
                        ". Available: " + product.getStock() + ", Requested: " + productItem.getQuantity());
            }

            // Calcular total
            total += product.getPrice() * productItem.getQuantity();
        }

        // 3. Crear y guardar el pago
        Payment payment = new Payment(total, saleRequest.getPaymentMethod(), LocalDateTime.now());
        payment = paymentRepository.save(payment);

        // 4. Crear y guardar la venta
        Sale sale = new Sale(LocalDate.now(), total, employee, payment);
        sale = saleRepository.save(sale);

        // 5. Actualizar el inventario (stock)
        for (SaleRequest.ProductItem productItem : saleRequest.getProducts()) {
            Product product = productRepository.findById(productItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + productItem.getProductId()));
            
            product.setStock(product.getStock() - productItem.getQuantity());
            productRepository.save(product);
        }

        return sale;
    }
}
