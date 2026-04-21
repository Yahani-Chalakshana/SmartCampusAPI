/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.exception.mapper;

/**
 *
 * @author Yahani
 */

import com.smartcampus.exception.LinkedResourceNotFoundException;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;
import java.util.Map;

// Registers the mapper to catch LinkedResourceNotFoundException
@Provider
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException> {
    @Override
    public Response toResponse(LinkedResourceNotFoundException e) {
        // Returns 422 status when a resource link (like roomId) is invalid
        return Response.status(422)
            .entity(Map.of(
                "error", "422 Unprocessable Entity",
                "message", e.getMessage(),
                "hint", "Ensure the roomId in your request body refers to an existing room."
            ))
            .type(MediaType.APPLICATION_JSON)
            .build();
    }
}