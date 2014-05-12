package it.cybion.socialeyeser;

import com.google.inject.Inject;
import it.cybion.commons.web.responses.exceptions.ServiceException;
import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Path("/twitter")
public class InfluencersService {
    
    private static final Logger LOGGER = Logger.getLogger(InfluencersService.class);
    
    @Inject
    public InfluencersService() {
    
        LOGGER.info("Starting influence service");
    }

    //TODO use local exception, remove web dependency
    @GET
    @Path("/{userId}")
    public InfluenceScore add(@PathParam("userId") String twitterUserId) throws ServiceException {
    
        try {
            LOGGER.info("Request for user " + twitterUserId);
            return new InfluenceScore(45.6);
        } catch (final Exception e) {
            throw new ServiceException("Error", e);
        }
        
    }
    
}