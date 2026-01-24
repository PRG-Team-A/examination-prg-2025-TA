module com.prg2025ta.project.examinationpgr2025ta {
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.web;
    requires tools.jackson.databind;
    requires spring.context;
    requires thymeleaf.spring6;
    requires thymeleaf;
    requires org.apache.tomcat.embed.core;
    requires spring.beans;
    requires spring.webmvc;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jetbrains.annotations;


    opens com.prg2025ta.project.examinationpgr2025ta to javafx.fxml;
    exports com.prg2025ta.project.examinationpgr2025ta.products;
    exports com.prg2025ta.project.examinationpgr2025ta.exceptions;
    exports com.prg2025ta.project.examinationpgr2025ta;
    exports com.prg2025ta.project.examinationpgr2025ta.api;
    exports com.prg2025ta.project.examinationpgr2025ta.api.models;

    opens templates;
}