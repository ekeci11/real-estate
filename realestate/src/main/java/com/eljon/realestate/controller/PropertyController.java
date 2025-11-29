package com.eljon.realestate.controller;

import com.eljon.realestate.model.Property;
import com.eljon.realestate.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    // ---------------- ADMIN ----------------

    @GetMapping
    public String listProperties(Model model) {
        model.addAttribute("properties", propertyService.getAllProperties());
        return "properties"; // properties.html (admin)
    }

    @GetMapping("/new")
    public String newPropertyForm(Model model) {
        model.addAttribute("property", new Property());
        return "property_form";
    }

    @GetMapping("/edit/{id}")
    public String editPropertyForm(@PathVariable Long id, Model model) {
        Property property = propertyService.getPropertyOrThrow(id);
        model.addAttribute("property", property);
        return "property_form";
    }

    @PostMapping
    public String saveProperty(@ModelAttribute Property property,
                               @RequestParam(value = "imageFiles", required = false) MultipartFile[] imageFiles) throws Exception {

        String projectDir = System.getProperty("user.dir");
        Path imageUploadPath = Paths.get(projectDir, "uploads", "images");
        Files.createDirectories(imageUploadPath);

        if (imageFiles != null) {
            List<String> imageFileNames = property.getImageFileNames() != null
                    ? property.getImageFileNames()
                    : new ArrayList<>();

            for (MultipartFile file : imageFiles) {
                if (!file.isEmpty()) {
                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    file.transferTo(imageUploadPath.resolve(fileName).toFile());
                    imageFileNames.add(fileName);
                }
            }
            property.setImageFileNames(imageFileNames);
        }

        propertyService.saveProperty(property);
        return "redirect:/properties";
    }

    @GetMapping("/delete/{id}")
    public String deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return "redirect:/properties";
    }

    // ---------------- PUBLIC ----------------

    @GetMapping("/public")
    public String publicProperties(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "status", required = false) String status,
            Model model
    ) {
        List<Property> properties;

        if (keyword != null && !keyword.trim().isEmpty()) {
            properties = propertyService.searchProperties(keyword);
        } else if ((category != null && !category.isEmpty()) ||
                (status != null && !status.isEmpty())) {
            properties = propertyService.getPropertiesByFilter(category, status);
        } else {
            properties = propertyService.getAllProperties();
        }

        model.addAttribute("properties", properties);
        model.addAttribute("categories", propertyService.getAllCategories());
        model.addAttribute("statuses", propertyService.getAllStatuses());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("keyword", keyword);

        return "properties-public";
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public Property getPropertyDetails(@PathVariable Long id) {
        return propertyService.getPropertyOrThrow(id);
    }
}
