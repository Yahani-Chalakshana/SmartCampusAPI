/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.store;

/**
 *
 * @author Yahani
 */

import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// Singleton class acting as an in-memory database for the application
public class DataStore {

    // Eager initialization of the single DataStore instance
    private static final DataStore INSTANCE = new DataStore();

    // Thread-safe maps to store rooms, sensors, and their readings
    private final Map<String, Room> rooms = new ConcurrentHashMap<>();
    private final Map<String, Sensor> sensors = new ConcurrentHashMap<>();
    private final Map<String, List<SensorReading>> readings = new ConcurrentHashMap<>();

    // Private constructor to prevent external instantiation
    private DataStore() {}

    // Provides global access to the singleton instance
    public static DataStore getInstance() {
        return INSTANCE;
    }

    // Getters for accessing the in-memory data collections
    public Map<String, Room> getRooms() { return rooms; }
    public Map<String, Sensor> getSensors() { return sensors; }
    public Map<String, List<SensorReading>> getReadings() { return readings; }
}