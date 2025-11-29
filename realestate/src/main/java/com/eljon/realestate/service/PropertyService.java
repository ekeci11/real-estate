package com.eljon.realestate.service;

import com.eljon.realestate.model.Property;
import com.eljon.realestate.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    // -------------------- CRUD --------------------

    // Merr të gjitha pronat, të renditura sipas ID (më të reja të parat)
    public List<Property> getAllProperties() {
        return propertyRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Property::getId).reversed())
                .collect(Collectors.toList());
    }

    // Merr një pronë sipas ID, ose hedh error
    public Property getPropertyOrThrow(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Property not found with id: " + id));
    }

    // Ruaj ose përditëso pronën
    public Property saveProperty(Property property) {
        return propertyRepository.save(property);
    }

    // Fshi pronën
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }

    // -------------------- FILTERS & LISTS --------------------

    // Merr kategoritë unike
    public List<String> getAllCategories() {
        return propertyRepository.findAll()
                .stream()
                .map(p -> p.getCategory() == null ? "" : p.getCategory().trim())
                .filter(s -> !s.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }

    // Merr statuset unike
    public List<String> getAllStatuses() {
        return propertyRepository.findAll()
                .stream()
                .map(p -> p.getStatus() == null ? "" : p.getStatus().trim())
                .filter(s -> !s.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }

    // Filtrim (kategori + status), dhe renditja (më të rejat të parat)
    public List<Property> getPropertiesByFilter(String category, String status) {
        return propertyRepository.findAll()
                .stream()
                .filter(p -> isMatch(p.getCategory(), category))
                .filter(p -> isMatch(p.getStatus(), status))
                .sorted(Comparator.comparing(Property::getId).reversed())
                .collect(Collectors.toList());
    }

    private boolean isMatch(String value, String filter) {
        return filter == null || filter.isEmpty()
                || (value != null && value.trim().equalsIgnoreCase(filter.trim()));
    }

    // -------------------- SEARCH --------------------

    // Search + renditja (më të rejat të parat)
    public List<Property> searchProperties(String keyword) {
        return propertyRepository.search(keyword.toLowerCase())
                .stream()
                .sorted(Comparator.comparing(Property::getId).reversed())
                .collect(Collectors.toList());
    }

    // Gjej pronat sipas kategorisë, me më të rejat të parat
    public List<Property> findByCategory(String category) {
        return propertyRepository.findByCategory(category)
                .stream()
                .sorted(Comparator.comparing(Property::getId).reversed())
                .collect(Collectors.toList());
    }
}
