/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.exception.mapper;

/**
 *
 * @author Yahani
 */

import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;
import java.util.Map;
import java.util.logging.Logger;

// Registers this class as a JAX-RS provider
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    // Logger to record server-side errors
    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionMapper.class.getName());

    @Override
    public Response toResponse(Throwable e) {
        // Logs the actual stack trace for the developer
        LOGGER.severe("Unexpected error: " + e.getMessage());
        // Returns a safe, generic 500 JSON error to the client
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(Map.of(
                "error", "500 Internal Server Error",
                "message", "An unexpected error occurred. Please contact the administrator."
            ))
            .type(MediaType.APPLICATION_JSON)
            .build();
    }
}