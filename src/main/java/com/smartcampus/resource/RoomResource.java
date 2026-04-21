/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

/**
 *
 * @author Yahani
 */

import com.smartcampus.exception.RoomNotEmptyException;
import com.smartcampus.model.Room;
import com.smartcampus.store.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

// Base URI path for room management
@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    // Access the shared singleton data store
    private final DataStore store = DataStore.getInstance();

    // Retrieve a list of all registered rooms
    @GET
    public Response getAllRooms() {
        List<Room> roomList = new ArrayList<>(store.getRooms().values());
        return Response.ok(roomList).build();
    }

    // Register a new room in the system
    @POST
    public Response createRoom(Room room) {
        // Validate that an ID is provided
        if (room.getId() == null || room.getId().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("error", "Room ID is required")).build();
        }
        // Prevent duplicate room IDs
        if (store.getRooms().containsKey(room.getId())) {
            return Response.status(Response.Status.CONFLICT)
                .entity(Map.of("error", "Room with this ID already exists")).build();
        }
        store.getRooms().put(room.getId(), room);
        return Response.status(Response.Status.CREATED).entity(room).build();
    }

    // Fetch details for a specific room by ID
    @GET
    @Path("/{roomId}")
    public Response getRoom(@PathParam("roomId") String roomId) {
        Room room = store.getRooms().get(roomId);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("error", "Room not found: " + roomId)).build();
        }
        return Response.ok(room).build();
    }

    // Remove a room from the system
    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        Room room = store.getRooms().get(roomId);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("error", "Room not found: " + roomId)).build();
        }
        // Business logic: Room must be empty of sensors before deletion
        if (!room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Room " + roomId + 
                " still has " + room.getSensorIds().size() + " sensor(s) assigned. Remove sensors first.");
        }
        store.getRooms().remove(roomId);
        return Response.ok(Map.of("message", "Room " + roomId + " deleted successfully")).build();
    }
}