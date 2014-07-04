package it.cybion.socialeyeser.trends.features.sma.windows;

import com.google.common.collect.EvictingQueue;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class FixedSizedFeatureWindow implements FeatureWindow {
    
    private EvictingQueue<EmittedFeature> features;
    private int containerSize;
    private double featuresSum;
    
    public FixedSizedFeatureWindow(int size) {
    
        this.features = EvictingQueue.create(size);
        
        // bootstrap queue
        for (int i = 0; i < size; i++)
            features.add(new EmittedFeature(-1, 0));
        
        this.containerSize = size;
        this.featuresSum = 0;
    }
    
    @Override
    public synchronized double pushFeature(EmittedFeature newestFeature) {
    
        EmittedFeature oldestFeature = features.poll();
        features.add(newestFeature);
        
        featuresSum = featuresSum - oldestFeature.getValue() + newestFeature.getValue();
        
        return featuresSum / containerSize;
        
    }
    
}
