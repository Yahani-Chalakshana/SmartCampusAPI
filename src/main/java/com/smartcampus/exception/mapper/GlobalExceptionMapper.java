// Location: src/main/java/com/smartcampus/exception/mapper/GlobalExceptionMapper.java
package com.smartcampus.exception.mapper;

import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;
import java.util.Map;
import java.util.logging.Logger;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionMapper.class.getName());

    @Override
    public Response toResponse(Throwable e) {
        LOGGER.severe("Unexpected error: " + e.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(Map.of(
                "error", "500 Internal Server Error",
                "message", "An unexpected error occurred. Please contact the administrator."
            ))
            .type(MediaType.APPLICATION_JSON)
            .build();
    }
}