package com.prg2025ta.project.examinationpgr2025ta.repository;

import com.prg2025ta.project.examinationpgr2025ta.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUsername(String username);
}
