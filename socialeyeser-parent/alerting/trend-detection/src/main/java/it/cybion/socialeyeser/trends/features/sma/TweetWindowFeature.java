package it.cybion.socialeyeser.trends.features.sma;

import it.cybion.socialeyeser.trends.features.Feature;
import it.cybion.socialeyeser.trends.features.sma.windows.EmittedFeature;
import it.cybion.socialeyeser.trends.features.sma.windows.FeatureWindow;
import it.cybion.socialeyeser.trends.features.sma.windows.FixedTimeFeatureWindow;
import it.cybion.socialeyeser.trends.model.Tweet;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

/*
 * this makes sense only with fixed time containers!
 */
public class TweetWindowFeature implements Feature {
    
    private FeatureWindow container;
    
    public TweetWindowFeature(FeatureWindow container) {
    
        this.container = container;
        
    }
    
    @Override
    public double extractFrom(Tweet tweet) {
    
        EmittedFeature feature = new EmittedFeature(tweet.createdAt.getTime(), 1);
        
        if (container instanceof FixedTimeFeatureWindow) {
            container.pushFeature(feature);
            return ((FixedTimeFeatureWindow) container).getFeatureSum();
        } else
            return container.pushFeature(feature);
    }
    
}
