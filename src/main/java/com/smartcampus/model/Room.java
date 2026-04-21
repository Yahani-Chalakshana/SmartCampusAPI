/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.model;

/**
 *
 * @author Yahani
 */

import java.util.ArrayList;
import java.util.List;

// Data model representing a physical room on campus
public class Room {
    private String id;
    private String name;
    private int capacity;
    // Maintains a list of sensor IDs linked to this specific room
    private List<String> sensorIds = new ArrayList<>();

    // Default constructor for JSON deserialization
    public Room() {}

    // Overloaded constructor for manual object creation
    public Room(String id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    // Standard getters and setters for data access and modification
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public List<String> getSensorIds() { return sensorIds; }
    public void setSensorIds(List<String> sensorIds) { this.sensorIds = sensorIds; }
}