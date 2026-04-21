/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.exception.mapper;

/**
 *
 * @author Yahani
 */

import com.smartcampus.exception.SensorUnavailableException;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;
import java.util.Map;

// Registers mapper to handle SensorUnavailableException
@Provider
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException> {
    @Override
    public Response toResponse(SensorUnavailableException e) {
        // Returns 403 Forbidden when a sensor is in MAINTENANCE mode
        return Response.status(Response.Status.FORBIDDEN)
            .entity(Map.of(
                "error", "403 Forbidden",
                "message", e.getMessage(),
                "hint", "Change sensor status to ACTIVE before posting readings."
            ))
            .type(MediaType.APPLICATION_JSON)
            .build();
    }
}