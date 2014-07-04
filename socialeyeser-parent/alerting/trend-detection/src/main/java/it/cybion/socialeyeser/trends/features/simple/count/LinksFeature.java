package it.cybion.socialeyeser.trends.features.simple.count;

import it.cybion.socialeyeser.trends.features.Feature;
import it.cybion.socialeyeser.trends.model.Tweet;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class LinksFeature implements Feature {
    
    @Override
    public double extractFrom(Tweet tweet) {
    
        double val = 0;
        if (tweet.entities.urls != null && tweet.entities.urls.length > 0)
            val = tweet.entities.urls.length;
        
        return val;
    }
    
}
