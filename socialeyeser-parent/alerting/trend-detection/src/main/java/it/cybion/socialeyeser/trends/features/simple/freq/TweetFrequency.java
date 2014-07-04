package it.cybion.socialeyeser.trends.features.simple.freq;

import it.cybion.socialeyeser.trends.features.Feature;
import it.cybion.socialeyeser.trends.model.Tweet;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class TweetFrequency implements Feature {
    
    private long lastTime = 0;
    
    public TweetFrequency() {
    
        this.lastTime = System.nanoTime();
    }
    
    @Override
    public double extractFrom(Tweet tweet) {
    
        long now = System.nanoTime();
        // 1 / (interarrival_time_nano/1000000000)
        double frequency = 1000000000.0 / (now - lastTime);
        
        lastTime = now;
        
        return Math.log10(frequency);
    }
}
