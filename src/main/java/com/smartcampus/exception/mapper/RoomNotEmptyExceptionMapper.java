/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.exception.mapper;

/**
 *
 * @author Yahani
 */

import com.smartcampus.exception.RoomNotEmptyException;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;
import java.util.Map;

// Registers mapper to handle RoomNotEmptyException globally
@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {
    @Override
    public Response toResponse(RoomNotEmptyException e) {
        // Returns 409 Conflict when attempting to delete a room with active sensors
        return Response.status(Response.Status.CONFLICT)
            .entity(Map.of(
                "error", "409 Conflict",
                "message", e.getMessage(),
                "hint", "Remove all sensors from the room before deleting it."
            ))
            .type(MediaType.APPLICATION_JSON)
            .build();
    }
}