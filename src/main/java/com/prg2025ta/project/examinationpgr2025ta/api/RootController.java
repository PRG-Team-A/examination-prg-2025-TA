package com.prg2025ta.project.examinationpgr2025ta.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class RootController {
    @RequestMapping("/")
    public RedirectView index() {
        return new RedirectView("/cart");
    }
}
