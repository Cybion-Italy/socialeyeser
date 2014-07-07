package it.cybion.socialeyeser.trends.features.windows;

import it.cybion.socialeyeser.trends.features.base.EmittedFeature;

import com.google.common.collect.EvictingQueue;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class FixedSizeWindow implements Window {
    
    private EvictingQueue<EmittedFeature> features;
    private int windowSize;
    private double featuresSum;
    
    public FixedSizeWindow(int size) {
    
        this.features = EvictingQueue.create(size);
        
        // bootstrap queue
        for (int i = 0; i < size; i++)
            features.add(new EmittedFeature(-1, 0));
        
        this.windowSize = size;
        this.featuresSum = 0;
    }
    
    @Override
    public synchronized double pushFeature(EmittedFeature newestFeature) {
    
        EmittedFeature oldestFeature = features.poll();
        features.add(newestFeature);
        
        featuresSum = featuresSum - oldestFeature.getValue() + newestFeature.getValue();
        
        return featuresSum / windowSize;
        
    }
    
    public int getWindowLength() {
    
        return windowSize;
    }
}
