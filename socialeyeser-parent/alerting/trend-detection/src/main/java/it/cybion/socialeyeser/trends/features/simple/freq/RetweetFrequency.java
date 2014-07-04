package it.cybion.socialeyeser.trends.features.simple.freq;

import it.cybion.socialeyeser.trends.features.Feature;
import it.cybion.socialeyeser.trends.model.Tweet;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class RetweetFrequency implements Feature {
    
    private long lastTime = 0;
    
    public RetweetFrequency() {
    
        this.lastTime = System.nanoTime();
    }
    
    @Override
    public double extractFrom(Tweet tweet) {
    
        double frequency = 0;
        
        if (tweet.retweetedStatus != null) {
            long now = System.nanoTime();
            // 1 / (interarrival_time_nano/1000000000)
            frequency = 1000000000.0 / (now - lastTime);
            
            lastTime = now;
            return frequency;
        }
        
        return frequency;
    }
}
