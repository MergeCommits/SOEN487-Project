package com.thing.rest;

import com.thing.core.Artist;
import com.thing.core.ArtistRepository;
import com.thing.impl.ArtistRepositoryImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("artist")
public class ArtistService {
    private static final ArtistRepository artistRepository = new ArtistRepositoryImpl();

    @GET
    @Path("all")
    @Produces(MediaType.TEXT_PLAIN)
    public Response listArtists() {
        return Response.ok(artistRepository.listArtists()).build();
    }

    @GET
    @Path("get/{nickname}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getArtist(@PathParam("nickname") String nickname) {
        Artist result = artistRepository.getArtist(nickname);
        if (result == null) {
            return Response.status(404).entity("Artist not found.").build();
        }

        return Response.ok(result.toString()).build();
    }

    @POST
    @Path("insert")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addArtist(@FormParam("nickname") String nickname,
                              @FormParam("first") String first,
                              @FormParam("last") String last,
                              @FormParam("bio") String bio) {
        Artist newArtist = new Artist(nickname, first, last, bio);

        boolean insertedSuccessfully = artistRepository.addArtist(newArtist);
        if (!insertedSuccessfully) {
            return Response.status(400).entity("Artist with that nickname already exists.").build();
        }

        return Response.ok(newArtist.toString()).build();
    }

    @PUT
    @Path("update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateArtist(@FormParam("nickname") String nickname,
                                 @FormParam("first") String first,
                                 @FormParam("last") String last,
                                 @FormParam("bio") String bio) {
        Artist newArtist = new Artist(nickname, first, last, bio);

        boolean updatedSuccessfully = artistRepository.updateArtist(newArtist);
        if (!updatedSuccessfully) {
            return Response.status(400).entity("Artist with that nickname does not exist.").build();
        }

        return Response.ok(newArtist.toString()).build();
    }

    @DELETE
    @Path("delete/{nickname}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteArtist(@PathParam("nickname") String nickname) {
        boolean deletedSuccessfully = artistRepository.removeArtist(nickname);
        if (!deletedSuccessfully) {
            return Response.status(400).entity("Artist with that nickname does not exist.").build();
        }

        return Response.ok("Artist deleted.").build();
    }
}
