package it.cybion.socialeyeser.trends.features.sma;

import it.cybion.socialeyeser.trends.features.Feature;
import it.cybion.socialeyeser.trends.features.sma.windows.EmittedFeature;
import it.cybion.socialeyeser.trends.features.sma.windows.FeatureWindow;
import it.cybion.socialeyeser.trends.model.Tweet;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class RetweetWindowFeature implements Feature {
    
    private FeatureWindow container;
    
    public RetweetWindowFeature(FeatureWindow container) {
    
        this.container = container;
        
    }
    
    @Override
    public double extractFrom(Tweet tweet) {
    
        EmittedFeature feature;
        if (tweet.retweetedStatus != null)
            feature = new EmittedFeature(tweet.createdAt.getTime(), 1);
        else
            feature = new EmittedFeature(tweet.createdAt.getTime(), 0);
        
        return container.pushFeature(feature);
    }
    
}