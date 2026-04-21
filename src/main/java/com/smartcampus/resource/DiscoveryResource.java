/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

/**
 *
 * @author Yahani
 */

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.LinkedHashMap;
import java.util.Map;

// Base path for the API discovery endpoint
@Path("/")
public class DiscoveryResource {

    // GET request to provide API metadata and resource links
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response discover() {
        // LinkedHashMap maintains the order of insertion for the JSON output
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("name", "Smart Campus Sensor & Room Management API");
        info.put("version", "v1");

        // Contact information for API support
        Map<String, String> contact = new LinkedHashMap<>();
        contact.put("owner", "Smart Campus Yahani");
        contact.put("email", "w2149629@westminster.ac.uk");
        info.put("contact", contact);

        // HATEOAS-style links to guide API consumers to available resources
        Map<String, String> resources = new LinkedHashMap<>();
        resources.put("self", "/api/v1");
        resources.put("rooms", "/api/v1/rooms");
        resources.put("sensors", "/api/v1/sensors");
        info.put("resources", resources);

        return Response.ok(info).build();
    }
}