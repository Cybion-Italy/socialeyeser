package it.cybion.socialeyeser.influence.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class EndpointException extends WebApplicationException {

    public EndpointException(final Response.Status status) {

        super(status);
    }

    public EndpointException(final String emsg) {

        super(Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(emsg)
                .build());
    }

}
