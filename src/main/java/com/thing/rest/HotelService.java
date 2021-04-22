package com.thing.rest;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.thing.core.Hotel;
import com.thing.core.HotelRepository;
import com.thing.impl.HotelRepositoryImpl;
import com.thing.runtime.Main;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("hotel")
public class HotelService {
    private static final HotelRepository hotelRepository = new HotelRepositoryImpl();

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
    public Response insertHotel(@FormParam("name") String name,
                                @FormParam("airConditioning") String airConditioning,
                                @FormParam("rating") String rating,
                                @FormParam("description") String description,
                                @FormParam("address") String address) {
        Hotel newHotel = new Hotel();
        newHotel.setName(name);
        newHotel.setAirConditioning(airConditioning);
        newHotel.setDescription(description);
        newHotel.setRating(rating);
        newHotel.setAddress(address);

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

    @POST
    @Path("email")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteHotel(@FormParam("hotel") String name, @FormParam("email") String emailAddress) {
        Hotel hotel = hotelRepository.getHotel(name);
        if (hotel == null) {
            return Response.status(400).entity("Hotel with that name does not exist.").build();
        }

        HTMLBuilder htmlBuilder = new HTMLBuilder();

        Email from = new Email(Main.configProperties.getProperty("from_email"));
        String subject = "Check out this hotel: " + hotel.getName();
        Email to = new Email(emailAddress);

        Content content = new Content(MediaType.TEXT_HTML, htmlBuilder.getHotelAsHTML(hotel));
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(Main.configProperties.getProperty("sendgrid_api_key"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            com.sendgrid.Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ignored) {

        }

        return Response.ok("Email sent.").build();
    }
}
