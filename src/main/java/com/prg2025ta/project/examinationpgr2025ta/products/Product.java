package com.prg2025ta.project.examinationpgr2025ta.products;

import java.util.UUID;

public interface Product {
    UUID getUuid();
    double getPrice();
    String getDisplayName();

    boolean equals(Product other);
}
