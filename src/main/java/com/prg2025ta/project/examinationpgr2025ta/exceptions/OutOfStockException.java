package com.prg2025ta.project.examinationpgr2025ta.exceptions;

public class OutOfStockException extends NullPointerException {
    public OutOfStockException() { this(""); }
    public OutOfStockException(String message) {
        super(message);
    }
}
