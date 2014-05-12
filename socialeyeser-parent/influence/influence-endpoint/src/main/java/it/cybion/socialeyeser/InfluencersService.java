package it.cybion.socialeyeser;

import java.util.HashMap;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

@Produces(MediaType.APPLICATION_JSON)
@Path("/twitter")
public class InfluencersService {
    
    private static final Logger LOGGER = Logger.getLogger(InfluencersService.class);
    
    private static final double MAX_FOLLOWERS = 1000000.0;
    private HashMap<String, Double> cache;
    private Random randomGenerator;
    
    @Inject
    public InfluencersService() {
    
        LOGGER.info("Starting influence service");
        randomGenerator = new Random(System.nanoTime());
    }
    
    @GET
    public InfluenceScore add(@QueryParam("userId") String userId,
            @QueryParam("followers") int followers) throws ServiceException {
    
        try {
            LOGGER.info("Request for user " + userId + " with " + followers + " followers");
            
            if (userId == null)
                throw new ServiceException("Missing parameter user id");
            if (followers <= 0)
                throw new ServiceException("Invalid follower number; should be positive");
            // ranging random scores [0.005, 1]
            double score = (randomGenerator.nextInt(1000) / 20000.0)
                    + (Math.log10(Math.min(followers, MAX_FOLLOWERS)) / Math.log10(MAX_FOLLOWERS))
                    - 0.05;
            if (score < 0)
                score *= -0.1;
            
            return new InfluenceScore(score);
            
        } catch (final Exception e) {
            throw new ServiceException("Error", e);
        }
        
    }
    
}