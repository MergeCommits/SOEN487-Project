package com.thing.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Arrays;

@Provider
public class UncaughtException extends Throwable implements ExceptionMapper<Throwable> {
    private static final long serialVersionUID = 1L;

    @Override
    public Response toResponse(Throwable exception) {
        return Response.status(500).entity(Arrays.toString(exception.getStackTrace())).type("text/plain").build();
    }
}
