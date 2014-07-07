package it.cybion.socialeyeser.trends.features.windows;

import it.cybion.socialeyeser.trends.features.base.EmittedFeature;

import java.util.LinkedList;
import java.util.List;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class FixedTimeFeatureWindow implements FeatureWindow {
    
    private List<EmittedFeature> features;
    private long windowLengthMillis;
    private double featuresSum;
    
    public FixedTimeFeatureWindow(long windowLengthMillis) {
    
        this.features = new LinkedList<EmittedFeature>();
        this.windowLengthMillis = windowLengthMillis;
        featuresSum = 0;
        
        features.add(new EmittedFeature(System.currentTimeMillis(), 0));
    }
    
    @Override
    public synchronized double pushFeature(EmittedFeature newestFeature) {
    
        int oldestFeatureIndex = features.size() - 1;
        features.add(0, newestFeature);
        featuresSum += newestFeature.getValue();
        
        EmittedFeature oldestFeature = features.get(oldestFeatureIndex);
        long windowTimeSpan = newestFeature.getTimeMillis() - oldestFeature.getTimeMillis();
        if (windowTimeSpan > windowLengthMillis) {
            
            featuresSum -= oldestFeature.getValue();
            features.remove(oldestFeatureIndex);
            
        }
        
        return featuresSum / features.size();
    }
    
    public double getFeatureSum() {
    
        return featuresSum;
    }
}
