// Location: src/main/java/com/smartcampus/exception/mapper/SensorUnavailableExceptionMapper.java
package com.smartcampus.exception.mapper;

import com.smartcampus.exception.SensorUnavailableException;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;
import java.util.Map;

@Provider
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException> {
    @Override
    public Response toResponse(SensorUnavailableException e) {
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