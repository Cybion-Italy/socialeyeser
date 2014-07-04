package it.cybion.socialeyeser.trends.features.sma.containers;

import java.util.LinkedList;
import java.util.List;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class FixedTimeFeatureContainer implements FeatureContainer {
    
    private List<EmittedFeature> features;
    private long periodMillis;
    private double featuresSum;
    
    public FixedTimeFeatureContainer(long period) {
    
        this.features = new LinkedList<EmittedFeature>();
        this.periodMillis = period;
        featuresSum = 0;
        
        features.add(new EmittedFeature(System.currentTimeMillis(), 0));
    }
    
    @Override
    public synchronized double pushFeature(EmittedFeature newestFeature) {
    
        int oldestFeatureIndex = features.size() - 1;
        features.add(newestFeature);
        featuresSum += newestFeature.getValue();
        
        EmittedFeature oldestFeature = features.get(oldestFeatureIndex);
        if (newestFeature.getTimeMillis() - oldestFeature.getTimeMillis() > periodMillis) {
            
            featuresSum -= oldestFeature.getValue();
            features.remove(oldestFeatureIndex);
            
        }
        
        return featuresSum / features.size();
    }
    
    public double getFeatureSum() {
    
        return featuresSum;
    }
}
