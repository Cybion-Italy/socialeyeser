package it.cybion.socialeyeser.trends.features;

import it.cybion.socialeyeser.trends.features.base.EmittedFeature;
import it.cybion.socialeyeser.trends.features.base.Feature;
import it.cybion.socialeyeser.trends.features.windows.FixedSizeWindow;
import it.cybion.socialeyeser.trends.features.windows.FixedTimeWindow;
import it.cybion.socialeyeser.trends.features.windows.Window;
import it.cybion.socialeyeser.trends.model.Tweet;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

/*
 * this makes sense only with fixed time containers!
 */
public class TweetFeature implements Feature {
    
    private static final String FEATURE_NAME = "Tweets";
    private Window container;
    
    public TweetFeature(Window container) {
    
        this.container = container;
        
    }
    
    @Override
    public double extractFrom(Tweet tweet) {
    
        EmittedFeature feature = new EmittedFeature(tweet.createdAt.getTime(), 1);
        
        if (container instanceof FixedTimeWindow) {
            container.pushFeature(feature);
            return ((FixedTimeWindow) container).getFeatureSum();
        } else
            return container.pushFeature(feature);
    }
    
    @Override
    public String getHumanReadableName() {
    
        if (container instanceof FixedTimeWindow) {
            return FEATURE_NAME + " average rate in last "
                    + ((FixedTimeWindow) container).getHumanReadableWindowLength();
        } else
            return FEATURE_NAME + " average in last "
                    + ((FixedSizeWindow) container).getWindowLength() + " tweets";
    }
    
}
