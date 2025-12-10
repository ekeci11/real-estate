package com.eljon.realestate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {

    // ---------------- HOMEPAGE ----------------
    @GetMapping("/")
    public String home() {
        return "redirect:/properties/public"; // Ridrejton homepage te lista publike e pronave
    }

    // ---------------- STATIC PAGES ----------------
    @GetMapping("/about")
    public String about() {
        return "about"; // about.html
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact"; // contact.html
    }
}

