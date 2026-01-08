module com.prg2025ta.project.examinationpgr2025ta {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.prg2025ta.project.examinationpgr2025ta to javafx.fxml;
    exports com.prg2025ta.project.examinationpgr2025ta.products;
    exports com.prg2025ta.project.examinationpgr2025ta.exceptions;
    exports com.prg2025ta.project.examinationpgr2025ta;
}