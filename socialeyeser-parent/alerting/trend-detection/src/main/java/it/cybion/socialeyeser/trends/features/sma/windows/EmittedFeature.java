package it.cybion.socialeyeser.trends.features.sma.windows;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class EmittedFeature {
    
    private long timeMillis;
    private int value;
    
    public EmittedFeature(long timeMillis, int value) {
    
        super();
        this.timeMillis = timeMillis;
        this.value = value;
    }
    
    public long getTimeMillis() {
    
        return timeMillis;
    }
    
    public int getValue() {
    
        return value;
    }
}
