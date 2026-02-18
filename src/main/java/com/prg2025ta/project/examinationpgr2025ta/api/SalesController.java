package com.prg2025ta.project.examinationpgr2025ta.api;

import com.prg2025ta.project.examinationpgr2025ta.SalesClass;
import com.prg2025ta.project.examinationpgr2025ta.database.DatabaseOperations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/sales")
public class SalesController {
    private static final Logger log = LogManager.getLogger(SalesController.class);

    @GetMapping("")
    public String salesOverview(Model model) throws SQLException {
        DatabaseOperations databaseOperations = DatabaseOperations.getInstance();

        List<SalesClass> sales = databaseOperations.getAllSales();

        log.info("Last sale id: {}", sales.getLast().getSaleId());

        model.addAttribute("sales", sales);
        return "sales";
    }
}
