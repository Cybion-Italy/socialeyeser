package it.cybion.socialeyeser.trends.features.windows;

import it.cybion.socialeyeser.trends.features.base.EmittedFeature;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class FixedTimeWindow implements Window {
    
    private List<EmittedFeature> features;
    private long windowLengthMillis;
    private double featuresSum;
    
    public FixedTimeWindow(long windowLengthMillis) {
    
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
    
    public long getWindowLengthMillis() {
    
        return windowLengthMillis;
    }
    
    public String getHumanReadableWindowLength() {
    
        if (windowLengthMillis <= 60000L)
            return new BigDecimal(windowLengthMillis / 1000.0).setScale(1, RoundingMode.HALF_UP)
                    .toString() + " s";
        
        if (windowLengthMillis <= 3600000L)
            return new BigDecimal(windowLengthMillis / 60000.0).setScale(1, RoundingMode.HALF_UP)
                    .toString() + " m";
        
        if (windowLengthMillis <= 86400000)
            return new BigDecimal(windowLengthMillis / 3600000.0).setScale(1, RoundingMode.HALF_UP)
                    .toString() + " h";
        
        else
            return new BigDecimal(windowLengthMillis / 86400000.0)
                    .setScale(1, RoundingMode.HALF_UP).toString() + " d";
    }
}
