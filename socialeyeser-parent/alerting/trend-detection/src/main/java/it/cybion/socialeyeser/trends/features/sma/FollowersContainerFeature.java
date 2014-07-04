package it.cybion.socialeyeser.trends.features.sma;

import it.cybion.socialeyeser.trends.features.Feature;
import it.cybion.socialeyeser.trends.features.sma.containers.EmittedFeature;
import it.cybion.socialeyeser.trends.features.sma.containers.FeatureContainer;
import it.cybion.socialeyeser.trends.model.Tweet;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class FollowersContainerFeature implements Feature {
    
    private FeatureContainer container;
    
    public FollowersContainerFeature(FeatureContainer container) {
    
        this.container = container;
        
    }
    
    @Override
    public double extractFrom(Tweet tweet) {
    
        EmittedFeature feature = new EmittedFeature(tweet.createdAt.getTime(),
                (int) tweet.user.followersCount);
        
        return container.pushFeature(feature);
    }
    
}
