// Location: src/main/java/com/smartcampus/exception/mapper/RoomNotEmptyExceptionMapper.java
package com.smartcampus.exception.mapper;

import com.smartcampus.exception.RoomNotEmptyException;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;
import java.util.Map;

@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {
    @Override
    public Response toResponse(RoomNotEmptyException e) {
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