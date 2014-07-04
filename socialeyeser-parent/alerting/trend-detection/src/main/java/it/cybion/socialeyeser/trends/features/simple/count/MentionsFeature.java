package it.cybion.socialeyeser.trends.features.simple.count;

import it.cybion.socialeyeser.trends.features.Feature;
import it.cybion.socialeyeser.trends.model.Tweet;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class MentionsFeature implements Feature {
    
    @Override
    public double extractFrom(Tweet tweet) {
    
        double val = 0;
        if (tweet.entities.userMentions != null && tweet.entities.userMentions.length > 0)
            val = tweet.entities.userMentions.length;
        
        return val;
    }
    
}
