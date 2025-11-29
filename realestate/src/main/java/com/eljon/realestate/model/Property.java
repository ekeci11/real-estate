package com.eljon.realestate.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "property_images", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "image_file_name")
    private List<String> imageFileNames = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "property_videos", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "video_file_name")
    private List<String> videoFileNames = new ArrayList<>();

    private String title;
    private String address;
    private String city;
    private double price;
    private String status;
    private String rooms;
    private String category;
    private double area;
    private String description;
    private String location;

    // Getters dhe Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public List<String> getImageFileNames() { return imageFileNames; }
    public void setImageFileNames(List<String> imageFileNames) { this.imageFileNames = imageFileNames; }

    public List<String> getVideoFileNames() { return videoFileNames; }
    public void setVideoFileNames(List<String> videoFileNames) { this.videoFileNames = videoFileNames; }

    public String getCategory() {return category;}

    public void setCategory(String category) {this.category = category;}
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getRooms() {return rooms;}

    public void setRooms(String rooms) {this.rooms = rooms;}

    public double getArea() { return area; }
    public void setArea(double area) { this.area = area; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
