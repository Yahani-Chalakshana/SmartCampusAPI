/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.exception;

/**
 *
 * @author Yahani
 */

// Custom exception for cases where a linked resource (like a Room) is missing
public class LinkedResourceNotFoundException extends RuntimeException {
    
    // Constructor to pass the error message to the parent RuntimeException
    public LinkedResourceNotFoundException(String message) {
        super(message);
    }
}