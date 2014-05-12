package it.cybion.socialeyeser.alerting;

import com.google.inject.Inject;
import it.cybion.commons.web.responses.exceptions.ServiceException;
import it.cybion.socialeyeser.alerting.responses.Alert;
import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Path("/alerts")
public class AlertsResource {
    
    private static final Logger LOGGER = Logger.getLogger(AlertsResource.class);
    
    @Inject
    public AlertsResource() {
    
        LOGGER.info("Starting influence service");
    }

    //TODO use local exception, remove web dependency
    @GET
    @Path("/{userId}")
    public Alert add(@PathParam("userId") String twitterUserId) throws ServiceException {
    
        try {
            LOGGER.info("Request for user " + twitterUserId);
            return new Alert(45.6);
        } catch (final Exception e) {
            throw new ServiceException("Error", e);
        }
        
    }
    
}