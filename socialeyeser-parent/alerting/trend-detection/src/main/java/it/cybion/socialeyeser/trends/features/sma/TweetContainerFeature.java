package it.cybion.socialeyeser.trends.features.sma;

import it.cybion.socialeyeser.trends.features.Feature;
import it.cybion.socialeyeser.trends.features.sma.containers.EmittedFeature;
import it.cybion.socialeyeser.trends.features.sma.containers.FeatureContainer;
import it.cybion.socialeyeser.trends.features.sma.containers.FixedTimeFeatureContainer;
import it.cybion.socialeyeser.trends.model.Tweet;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

/*
 * this makes sense only with fixed time containers!
 */
public class TweetContainerFeature implements Feature {
    
    private FeatureContainer container;
    
    public TweetContainerFeature(FeatureContainer container) {
    
        this.container = container;
        
    }
    
    @Override
    public double extractFrom(Tweet tweet) {
    
        EmittedFeature feature = new EmittedFeature(tweet.createdAt.getTime(), 1);
        
        if (container instanceof FixedTimeFeatureContainer) {
            container.pushFeature(feature);
            return ((FixedTimeFeatureContainer) container).getFeatureSum();
        } else
            return container.pushFeature(feature);
    }
    
}
