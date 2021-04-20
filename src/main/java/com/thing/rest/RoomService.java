package com.thing.rest;

import com.thing.core.Hotel;
import com.thing.core.HotelRepository;
import com.thing.core.Room;
import com.thing.impl.HotelRepositoryImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RoomService {
    private static final HotelRepository hotelRepository = new HotelRepositoryImpl();

    @GET
    @Path("get/{room}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoom(@PathParam("room") int roomNumber) {
        Room result = hotelRepository.getRoom(roomNumber);
        if (result == null) {
            return Response.status(404).entity("Room not found.").build();
        }

        return Response.ok(result).build();
    }

    @POST
    @Path("insert")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertRoom(@FormParam("room") int roomNumber, @FormParam("floor") String floor, @FormParam("hotel") String hotelName) {
        Room newRoom = new Room();
        newRoom.setRoomNumber(roomNumber);
        newRoom.setFloor(floor);

        boolean addedSuccessfully = hotelRepository.addOrReplaceRoom(newRoom, hotelName);
        if (!addedSuccessfully) {
            return Response.status(400).entity("Hotel with that name does not exist.").build();
        }

        return Response.ok(newRoom).build();
    }

    @DELETE
    @Path("delete/{room}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRoom(@PathParam("room") int roomNumber) {
        boolean deletedSuccessfully = hotelRepository.removeRoom(roomNumber);
        if (!deletedSuccessfully) {
            return Response.status(400).entity("Room with that name does not exist.").build();
        }

        return Response.ok("Room deleted.").build();
    }
}
