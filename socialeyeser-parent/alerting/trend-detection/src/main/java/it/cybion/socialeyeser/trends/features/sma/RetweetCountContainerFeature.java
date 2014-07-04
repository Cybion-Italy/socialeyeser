package it.cybion.socialeyeser.trends.features.sma;

import it.cybion.socialeyeser.trends.features.Feature;
import it.cybion.socialeyeser.trends.features.sma.containers.EmittedFeature;
import it.cybion.socialeyeser.trends.features.sma.containers.FeatureContainer;
import it.cybion.socialeyeser.trends.model.Tweet;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class RetweetCountContainerFeature implements Feature {
    
    private FeatureContainer container;
    
    public RetweetCountContainerFeature(FeatureContainer container) {
    
        this.container = container;
        
    }
    
    @Override
    public double extractFrom(Tweet tweet) {
    
        EmittedFeature feature;
        if (tweet.retweetedStatus != null)
            feature = new EmittedFeature(tweet.createdAt.getTime(),
                    (int) tweet.retweetedStatus.retweetCount);
        else
            feature = new EmittedFeature(tweet.createdAt.getTime(), 0);
        
        return container.pushFeature(feature);
    }
    
}
