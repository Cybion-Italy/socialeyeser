package it.cybion.socialeyeser.trends.features;

import it.cybion.socialeyeser.trends.features.base.EmittedFeature;
import it.cybion.socialeyeser.trends.features.base.Feature;
import it.cybion.socialeyeser.trends.features.windows.Window;
import it.cybion.socialeyeser.trends.model.Tweet;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class RetweetsFeature implements Feature {
    
    private Window window;
    
    public RetweetsFeature(Window container) {
    
        this.window = container;
        
    }
    
    @Override
    public double extractFrom(Tweet tweet) {
    
        EmittedFeature feature;
        if (tweet.retweetedStatus != null)
            feature = new EmittedFeature(tweet.createdAt.getTime(),
                    (int) tweet.retweetedStatus.retweetCount);
        else
            feature = new EmittedFeature(tweet.createdAt.getTime(), 0);
        
        return window.pushFeature(feature);
    }
    
}
