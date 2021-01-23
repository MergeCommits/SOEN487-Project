package com.thing.rest;

import com.thing.core.Album;
import com.thing.core.AlbumRepository;
import com.thing.impl.AlbumRepositoryImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("album")
public class AlbumService {
    private static final AlbumRepository albumRepository = new AlbumRepositoryImpl();

    @GET
    @Path("all")
    @Produces(MediaType.TEXT_PLAIN)
    public Response listAlbums() {
        return Response.ok(albumRepository.listAlbums()).build();
    }

    @GET
    @Path("get/{isrc}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getAlbum(@PathParam("isrc") String isrc) {
        Album result = albumRepository.getAlbum(isrc);
        if (result == null) {
            return Response.status(404).entity("Album not found.").build();
        }

        return Response.ok(result.toString()).build();
    }

    @POST
    @Path("insert")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addAlbum(@FormParam("isrc") String isrc,
                             @FormParam("title") String title,
                             @FormParam("year") int year,
                             @FormParam("desc") String description) {
        Album newAlbum = new Album(isrc, title, year, description);

        boolean insertedSuccessfully = albumRepository.addAlbum(newAlbum);
        if (!insertedSuccessfully) {
            return Response.status(400).entity("Album with that isrc already exists.").build();
        }

        return Response.ok(newAlbum.toString()).build();
    }

    @PUT
    @Path("update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateAlbum(@FormParam("isrc") String isrc,
                                @FormParam("title") String title,
                                @FormParam("year") int year,
                                @FormParam("artist") String artist,
                                @FormParam("desc") String description) {
        Album newAlbum = new Album(isrc, title, year, description);

        boolean updatedSuccessfully = albumRepository.updateAlbum(newAlbum);
        if (!updatedSuccessfully) {
            return Response.status(400).entity("Album with that isrc does not exist.").build();
        }

        return Response.ok(newAlbum.toString()).build();
    }

    @DELETE
    @Path("delete/{isrc}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteAlbum(@PathParam("isrc") String isrc) {
        boolean deletedSuccessfully = albumRepository.removeAlbum(isrc);
        if (!deletedSuccessfully) {
            return Response.status(400).entity("Album with that isrc does not exist.").build();
        }

        return Response.ok("Album deleted. ").build();
    }
}
