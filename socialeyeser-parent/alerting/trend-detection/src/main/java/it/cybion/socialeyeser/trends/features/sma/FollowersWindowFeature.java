package it.cybion.socialeyeser.trends.features.sma;

import it.cybion.socialeyeser.trends.features.Feature;
import it.cybion.socialeyeser.trends.features.sma.windows.EmittedFeature;
import it.cybion.socialeyeser.trends.features.sma.windows.FeatureWindow;
import it.cybion.socialeyeser.trends.model.Tweet;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class FollowersWindowFeature implements Feature {
    
    private FeatureWindow container;
    
    public FollowersWindowFeature(FeatureWindow container) {
    
        this.container = container;
        
    }
    
    @Override
    public double extractFrom(Tweet tweet) {
    
        EmittedFeature feature = new EmittedFeature(tweet.createdAt.getTime(),
                (int) tweet.user.followersCount);
        
        return container.pushFeature(feature);
    }
    
}
