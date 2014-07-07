package it.cybion.socialeyeser.trends.features;

import it.cybion.socialeyeser.trends.features.base.EmittedFeature;
import it.cybion.socialeyeser.trends.features.base.Feature;
import it.cybion.socialeyeser.trends.features.windows.FeatureWindow;
import it.cybion.socialeyeser.trends.model.Tweet;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class FollowingsFeature implements Feature {
    
    private FeatureWindow container;
    
    public FollowingsFeature(FeatureWindow container) {
    
        this.container = container;
        
    }
    
    @Override
    public double extractFrom(Tweet tweet) {
    
        EmittedFeature feature = new EmittedFeature(tweet.createdAt.getTime(),
                (int) tweet.user.friendsCount);
        
        return container.pushFeature(feature);
    }
    
}
