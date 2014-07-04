package it.cybion.socialeyeser.trends.features;

import it.cybion.socialeyeser.trends.model.Tweet;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public interface Feature {
    
    double extractFrom(Tweet tweet);
}
