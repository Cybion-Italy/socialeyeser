package it.cybion.socialeyeser.alerting.exceptionmappers;

import com.google.inject.Singleton;
import it.cybion.socialeyeser.alerting.representations.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.NoSuchElementException;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
@Provider
@Singleton
public class NoSuchElementExceptionMapper implements ExceptionMapper<NoSuchElementException> {

    @Override
    public Response toResponse(NoSuchElementException exception) {

        final ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND)
                .entity(errorMessage)
                .build();
    }
}
