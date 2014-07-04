package it.cybion.socialeyeser.trends.features.simple.count;

import it.cybion.socialeyeser.trends.features.Feature;
import it.cybion.socialeyeser.trends.model.Tweet;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class FollowingFeature implements Feature {
    
    @Override
    public double extractFrom(Tweet tweet) {
    
        return tweet.user.friendsCount;
    }
    
}
