package com.prg2025ta.project.examinationpgr2025ta.repository;

import com.prg2025ta.project.examinationpgr2025ta.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
