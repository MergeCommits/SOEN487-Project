package com.thing.rest;

import com.thing.core.Album;
import com.thing.core.AlbumCoverImage;
import com.thing.core.AlbumRepository;
import com.thing.core.Artist;
import com.thing.impl.AlbumRepositoryImpl;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Path("album")
public class AlbumService {
    private static final AlbumRepository albumRepository = new AlbumRepositoryImpl();

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAlbums() {
        GenericEntity<List<Album>> list = new GenericEntity<List<Album>>(albumRepository.allAlbums()) {};
        return Response.ok(list).build();
    }

    @GET
    @Path("get/{isrc}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAlbum(@PathParam("isrc") String isrc) {
        Album result = albumRepository.getAlbum(isrc);
        if (result == null) {
            return Response.status(404).entity("Album not found.").build();
        }

        return Response.ok(result).build();
    }

    @POST
    @Path("insert")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAlbum(@FormParam("isrc") String isrc,
                             @FormParam("title") String title,
                             @FormParam("year") int year,
                             @FormParam("firstName") String firstName,
                             @FormParam("lastName") String lastName,
                             @FormParam("desc") String description) {
        Album newAlbum = new Album();
        newAlbum.setIsrc(isrc);
        newAlbum.setTitle(title);
        newAlbum.setYear(year);
        newAlbum.setDescription(description);

        Artist artist = new Artist();
        artist.setFirstName(firstName);
        artist.setLastName(lastName);
        newAlbum.setArtist(artist);

        boolean insertedSuccessfully = albumRepository.addAlbum(newAlbum);
        if (!insertedSuccessfully) {
            return Response.status(400).entity("Album with that isrc already exists.").build();
        }

        return Response.ok(newAlbum).build();
    }

    @PUT
    @Path("update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAlbum(@FormParam("isrc") String isrc,
                                @FormParam("title") String title,
                                @FormParam("year") int year,
                                @FormParam("firstName") String firstName,
                                @FormParam("lastName") String lastName,
                                @FormParam("desc") String description) {
        Album newAlbum = new Album();
        newAlbum.setIsrc(isrc);
        newAlbum.setTitle(title);
        newAlbum.setYear(year);
        newAlbum.setDescription(description);

        Artist artist = new Artist();
        artist.setFirstName(firstName);
        artist.setLastName(lastName);
        newAlbum.setArtist(artist);

        boolean updatedSuccessfully = albumRepository.updateAlbum(newAlbum);
        if (!updatedSuccessfully) {
            return Response.status(400).entity("Album with that isrc does not exist.").build();
        }

        return Response.ok(newAlbum).build();
    }

    @DELETE
    @Path("delete/{isrc}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAlbum(@PathParam("isrc") String isrc) {
        boolean deletedSuccessfully = albumRepository.removeAlbum(isrc);
        if (!deletedSuccessfully) {
            return Response.status(400).entity("Album with that isrc does not exist.").build();
        }

        return Response.ok("Album deleted.").build();
    }

    @POST
    @Path("/cover/update/{isrc}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateCoverImage(@PathParam("isrc") String isrc,
                                     @FormDataParam("file") InputStream uploadedInputStream,
                                     @FormDataParam("file") FormDataContentDisposition fileDetail,
                                     @FormDataParam("file") final FormDataBodyPart body) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = uploadedInputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        byte[] byteArray = buffer.toByteArray();

        AlbumCoverImage albumCoverImage = new AlbumCoverImage();
        albumCoverImage.setImage(byteArray);
        albumCoverImage.setMimeType(body.getMediaType().toString());

        albumRepository.updateAlbumImage(isrc, albumCoverImage);
        return Response.ok("Cover added.").build();
    }

    @GET
    @Path("/cover/get/{isrc}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response getCoverImage(@PathParam("isrc") String isrc) {
        AlbumCoverImage albumCoverImage = albumRepository.getAlbumImage(isrc);
        return Response.ok(albumCoverImage).build();
    }

    @DELETE
    @Path("/cover/delete/{isrc}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteCoverImage(@PathParam("isrc") String isrc) {
        albumRepository.removeAlbumImage(isrc);
        return Response.ok("Album cover deleted.").build();
    }
}
