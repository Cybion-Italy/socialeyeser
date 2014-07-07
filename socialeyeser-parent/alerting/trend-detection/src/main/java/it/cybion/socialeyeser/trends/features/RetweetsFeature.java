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

public class RetweetsFeature implements Feature {
    
    private static final String FEATURE_NAME = "Cumulative Retweets";
    private Window container;
    
    public RetweetsFeature(Window container) {
    
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
