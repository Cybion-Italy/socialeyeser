package it.cybion.socialeyeser.influence.resources;

import com.google.inject.Inject;
import it.cybion.socialeyeser.influence.exceptions.EndpointException;
import it.cybion.socialeyeser.influence.representations.InfluenceScore;
import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Random;

@Produces(MediaType.APPLICATION_JSON)
@Path("/twitter")
public class InfluencersResource {

    private static final Logger LOGGER = Logger.getLogger(InfluencersResource.class);

    private static final double MAX_FOLLOWERS = 1000000.0;

    private Random randomGenerator;

    @Inject
    public InfluencersResource() {

        LOGGER.info("Starting influence service");
        //TODO is this the reason for singleton scope?
        //TODO inject as dependency
        randomGenerator = new Random(System.nanoTime());
    }

    @GET
    public InfluenceScore add(@QueryParam("userId") String userId,
            @QueryParam("followers") int followers) throws EndpointException {

        LOGGER.info("Request for user " + userId + " with " + followers + " followers");

        if (userId == null) {
            final String emsg = "Bad request: missing parameter user id";
            throw new EndpointException(emsg);
        }
        if (followers <= 0) {
            final String emsg =
                    "Bad request: invalid follower number: '" + followers + "' should be positive";
            throw new EndpointException(emsg);
        }
        // ranging random scores [0.005, 1]
        double score = (randomGenerator.nextInt(1000) / 20000.0) + (Math.log10(Math.min(followers,
                MAX_FOLLOWERS)) / Math.log10(MAX_FOLLOWERS)) - 0.05;
        if (score < 0) {
            score *= -0.1;
        }

        return new InfluenceScore(score);

    }

}