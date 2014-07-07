package it.cybion.socialeyeser.trends.features.simple.count;

import it.cybion.socialeyeser.trends.features.Feature;
import it.cybion.socialeyeser.trends.model.Tweet;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class FavoritesFeature implements Feature {
    
    @Override
    public double extractFrom(Tweet tweet) {
    
        double val = 0;
        
        if (tweet.retweetedStatus != null)
            val = tweet.retweetedStatus.favoriteCount;
        
        return val;
    }
    
}