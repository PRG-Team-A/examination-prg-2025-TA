package com.prg2025ta.project.examinationpgr2025ta.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {
    @GetMapping
    public String person(Model model) {
        model.addAttribute("message", "Hello from the controller!");
        return "product";
    }
}
