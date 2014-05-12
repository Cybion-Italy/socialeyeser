package it.cybion.socialeyeser;

import java.util.HashMap;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

@Produces(MediaType.APPLICATION_JSON)
@Path("/twitter")
public class InfluencersService {
    
    private static final Logger LOGGER = Logger.getLogger(InfluencersService.class);
    
    private HashMap<String, Double> cache;
    private Random randomGenerator;
    
    @Inject
    public InfluencersService() {
    
        LOGGER.info("Starting influence service");
        cache = new HashMap<String, Double>();
        randomGenerator = new Random(System.nanoTime());
    }
    
    @GET
    @Path("/{userId}")
    public InfluenceScore add(@PathParam("userId") String twitterUserId) throws ServiceException {
    
        try {
            LOGGER.info("Request for user " + twitterUserId);
            
            if (cache.containsKey(twitterUserId))
                return new InfluenceScore(cache.get(twitterUserId));
            else {
                
                // ranging random scores [0.01, 0.51]
                double score = (randomGenerator.nextInt(5000) / 10000.0) + 0.01;
                cache.put(twitterUserId, score);
                return new InfluenceScore(cache.get(twitterUserId));
            }
        } catch (final Exception e) {
            throw new ServiceException("Error", e);
        }
        
    }
    
}