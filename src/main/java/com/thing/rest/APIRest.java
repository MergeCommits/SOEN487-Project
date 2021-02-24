
package com.thing.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("rest-api")
public class APIRest {
    PostingBean postingBean = PostingBean.getInstance();

    /**
     * Meant for returning the list of customers
     * @return A concatenation of the toString method for all customers
     */
    @GET
    @Path("ad-service")
    public Response adServiceGet() {
        return Response.ok(postingBean.getPosting().toString()).build();
    }

    /**
     * Meant for returning the list of customers
     * @return A concatenation of the toString method for all customers
     */
    @GET
    @Path("ad-service/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response adServiceGetJSON() {
        return Response.ok(postingBean.getPosting()).build();
    }

    /**
     * Meant for returning the list of customers
     * @return A concatenation of the toString method for all customers
     */
    @GET
    @Path("ad-service/xml")
    @Produces(MediaType.APPLICATION_XML)
    public Response adServiceGetXML() {
        return Response.ok(postingBean.getPosting()).build();
    }

    /**
     * Meant for returning the list of customers
     * @return A concatenation of the toString method for all customers
     */
    @POST
    @Path("ad-service/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response adServiceGetXML(Ad ad) {
        postingBean.setPosting(ad);
        return Response.ok("Ad posted").build();
    }

    /**
     * Meant for returning the list of customers
     * @return A concatenation of the toString method for all customers
     */
    @POST
    @Path("ad-service/text")
    public Response adServiceGetXML(@QueryParam("title") String title, String body) {
        Ad ad = new Ad();
        ad.setTitle(title);
        ad.setText(body);
        postingBean.setPosting(ad);
        return Response.ok("Ad posted").build();
    }
}
