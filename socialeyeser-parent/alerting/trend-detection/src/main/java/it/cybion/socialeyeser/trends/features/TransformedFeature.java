package it.cybion.socialeyeser.trends.features;

import it.cybion.socialeyeser.trends.model.Tweet;

import com.google.common.base.Function;

public class TransformedFeature implements Feature {
    
    private Feature baseFeature;
    Function<Double, Double> function;
    
    public TransformedFeature(Feature feature, Function<Double, Double> function) {
    
        this.baseFeature = feature;
        this.function = function;
    }
    
    @Override
    public double extractFrom(Tweet tweet) {
    
        return function.apply(baseFeature.extractFrom(tweet));
    }
    
}
