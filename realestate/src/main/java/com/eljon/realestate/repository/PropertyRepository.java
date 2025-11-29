package com.eljon.realestate.repository;

import com.eljon.realestate.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findByCategory(String category);

    @Query("SELECT p FROM Property p WHERE " +
            "LOWER(p.title) LIKE CONCAT('%', :keyword, '%') OR " +
            "LOWER(p.description) LIKE CONCAT('%', :keyword, '%') OR " +
            "LOWER(p.location) LIKE CONCAT('%', :keyword, '%') " +
            "ORDER BY p.id DESC")
    List<Property> search(@Param("keyword") String keyword);

    @Query("SELECT p FROM Property p ORDER BY p.id DESC")
    List<Property> findAllWithImages();
}
