/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.exception;

/**
 *
 * @author Yahani
 */

// Custom exception for sensors that are in maintenance or offline
public class SensorUnavailableException extends RuntimeException {
    
    // Passes the specific unavailability reason to the parent class
    public SensorUnavailableException(String message) {
        super(message);
    }
}