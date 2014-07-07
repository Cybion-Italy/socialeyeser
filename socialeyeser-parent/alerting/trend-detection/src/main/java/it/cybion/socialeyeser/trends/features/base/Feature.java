package it.cybion.socialeyeser.trends.features.base;

import it.cybion.socialeyeser.trends.model.Tweet;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public interface Feature {
    
    public double extractFrom(Tweet tweet);
    
    public String getHumanReadableName();
}
