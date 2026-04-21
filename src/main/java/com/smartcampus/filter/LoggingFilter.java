/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.filter;

/**
 *
 * @author Yahani
 */

import javax.ws.rs.container.*;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

// Register as a JAX-RS provider to intercept traffic
@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger LOGGER = Logger.getLogger(LoggingFilter.class.getName());

    // Intercepts and logs incoming HTTP requests
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        LOGGER.info("REQUEST: " + requestContext.getMethod() + " " + requestContext.getUriInfo().getRequestUri());
    }

    // Intercepts and logs outgoing HTTP responses
    @Override
    public void filter(ContainerRequestContext requestContext, 
                       ContainerResponseContext responseContext) throws IOException {
        LOGGER.info("RESPONSE: " + responseContext.getStatus() + " for " +
            requestContext.getMethod() + " " + requestContext.getUriInfo().getRequestUri());
    }
}