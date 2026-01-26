package com.prg2025ta.project.examinationpgr2025ta.exceptions;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException() {
        this("Out of stock");
    }

    public OutOfStockException(String message) {
        super(message);
    }
}
