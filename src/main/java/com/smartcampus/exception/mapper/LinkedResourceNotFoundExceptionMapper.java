// Location: src/main/java/com/smartcampus/exception/mapper/LinkedResourceNotFoundExceptionMapper.java
package com.smartcampus.exception.mapper;

import com.smartcampus.exception.LinkedResourceNotFoundException;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;
import java.util.Map;

@Provider
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException> {
    @Override
    public Response toResponse(LinkedResourceNotFoundException e) {
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