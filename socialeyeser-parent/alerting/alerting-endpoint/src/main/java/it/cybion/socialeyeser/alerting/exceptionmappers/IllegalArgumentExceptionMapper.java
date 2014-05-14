package it.cybion.socialeyeser.alerting.exceptionmappers;

import com.google.inject.Singleton;
import it.cybion.socialeyeser.alerting.representations.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
@Provider
@Singleton
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException exception) {

        final ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMessage)
                .build();
    }
}