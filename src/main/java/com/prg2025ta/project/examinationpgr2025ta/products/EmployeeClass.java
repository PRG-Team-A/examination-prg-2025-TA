package com.prg2025ta.project.examinationpgr2025ta.products;

import java.util.Objects;
import java.util.Optional;

/**
 * Clase que representa un empleado con su identificador y datos básicos.
 */
public class EmployeeClass {

    private int id;
    private String name;
    private String email;
    private String department;
    private double salary;

    /**
     * Constructor por defecto.
     */
    public EmployeeClass() {
        this.id = 0;
        this.name = "";
        this.email = "";
        this.department = "";
        this.salary = 0.0;
    }

    /**
     * Constructor con id y nombre.
     */
    public EmployeeClass(int id, String name) {
        this.id = id;
        this.name = name != null ? name : "";
        this.email = "";
        this.department = "";
        this.salary = 0.0;
    }

    /**
     * Constructor completo con todos los campos.
     */
    public EmployeeClass(int id, String name, String email, String department, double salary) {
        this.id = id;
        this.name = name != null ? name : "";
        this.email = email != null ? email : "";
        this.department = department != null ? department : "";
        this.salary = salary;
    }

    // --- Getters ---

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    /**
     * Devuelve el email como Optional (útil si puede estar vacío).
     */
    public Optional<String> getEmailOptional() {
        return (email == null || email.isEmpty()) ? Optional.empty() : Optional.of(email);
    }

    /**
     * Devuelve el departamento como Optional.
     */
    public Optional<String> getDepartmentOptional() {
        return (department == null || department.isEmpty()) ? Optional.empty() : Optional.of(department);
    }

    // --- Setters ---

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name != null ? name : "";
    }

    public void setEmail(String email) {
        this.email = email != null ? email : "";
    }

    public void setDepartment(String department) {
        this.department = department != null ? department : "";
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    // --- equals, hashCode, toString ---

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EmployeeClass other = (EmployeeClass) obj;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "EmployeeClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                '}';
    }
}
