package com.smartcampus.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Path("/")
public class DiscoveryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response discover() {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("name", "Smart Campus Sensor & Room Management API");
        info.put("version", "v1");

        Map<String, String> contact = new LinkedHashMap<>();
        contact.put("owner", "Smart Campus Yahani");
        contact.put("email", "w2149629@westminster.ac.uk");
        info.put("contact", contact);

        Map<String, String> resources = new LinkedHashMap<>();
        resources.put("self", "/api/v1");
        resources.put("rooms", "/api/v1/rooms");
        resources.put("sensors", "/api/v1/sensors");
        info.put("resources", resources);

        return Response.ok(info).build();
    }
}