package com.thing.rest;

import com.thing.runtime.Main;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;

@PreMatching
@Provider
public class IncomingRequestFilter implements ContainerRequestFilter {

    @Context
    private UriInfo info;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String check = info.getPath();
        if (check.endsWith("pretty-get")) {
            return;
        }

        String auth = requestContext.getHeaderString("Authorization");

        if (auth == null || auth.isEmpty()) {
            requestContext.abortWith(Response.status(
                Response.Status.UNAUTHORIZED).build());
            return;
        }

        byte[] decodedBytes = Base64.getDecoder().decode(auth.split(" ")[1]);
        String decodedString = new String(decodedBytes);
        String username = decodedString.split(":")[0];
        String pass = decodedString.split(":")[1];

        if (
            !username.equals(Main.configProperties.getProperty("username", ""))
                || !pass.equals(Main.configProperties.getProperty("password", ""))
        ) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("User not found.").build());
        }
    }
}
