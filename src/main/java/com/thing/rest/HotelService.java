package com.thing.rest;

import com.thing.core.Hotel;
import com.thing.core.HotelRepository;
import com.thing.impl.HotelRepositoryImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("hotel")
public class HotelService {
    private static final HotelRepository hotelRepository = new HotelRepositoryImpl();

//    @GET
//    @Path("all")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response listAlbums() {
//        GenericEntity<List<Album>> list = new GenericEntity<List<Album>>(albumRepository.allAlbums()) {};
//        return Response.ok(list).build();
//    }

    @GET
    @Path("pretty-get")
    @Produces(MediaType.TEXT_HTML)
    public Response getHTMLHotel(@QueryParam("name") String name) {
        Hotel result = hotelRepository.getHotel(name);
        if (result == null) {
            return Response.status(404).entity("Hotel not found.").build();
        }

        HTMLBuilder htmlBuilder = new HTMLBuilder();
        return Response.ok(htmlBuilder.getHotelAsHTML(result)).build();
    }

    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHotel(@QueryParam("name") String name) {
        Hotel result = hotelRepository.getHotel(name);
        if (result == null) {
            return Response.status(404).entity("Hotel not found.").build();
        }

        return Response.ok(result).build();
    }

    @POST
    @Path("insert")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertHotel(@FormParam("name") String name) {
        Hotel newHotel = new Hotel();
        newHotel.setName(name);

        hotelRepository.addOrReplaceHotel(newHotel);
        return Response.ok(newHotel).build();
    }

    @POST
    @Path("delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteHotel(@FormParam("name") String name) {
        boolean deletedSuccessfully = hotelRepository.removeHotel(name);
        if (!deletedSuccessfully) {
            return Response.status(400).entity("Hotel with that name does not exist.").build();
        }

        return Response.ok("Hotel deleted.").build();
    }
}
