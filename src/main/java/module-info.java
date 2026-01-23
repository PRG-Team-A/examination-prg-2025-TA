module com.prg2025ta.project.examinationpgr2025ta {
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.web;
    requires tools.jackson.databind;
    requires spring.context;
    exports com.prg2025ta.project.examinationpgr2025ta.products;
    exports com.prg2025ta.project.examinationpgr2025ta.exceptions;
    exports com.prg2025ta.project.examinationpgr2025ta;
    exports com.prg2025ta.project.examinationpgr2025ta.api;
}