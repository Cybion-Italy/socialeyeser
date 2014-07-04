package it.cybion.socialeyeser.trends.features.sma.windows;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class AggregatedFeature {
    
    private long timeMillisStart;
    private long timeMillisEnd;
    private double value;
    private int valuesSize;
    
    public AggregatedFeature(long timeMillisStart, long timeMillisEnd) {
    
        super();
        this.timeMillisStart = timeMillisStart;
        this.timeMillisEnd = timeMillisEnd;
        this.value = 0;
        this.valuesSize = 0;
    }
    
    public void pushValue(double value) {
    
        this.value += value;
        this.valuesSize++;
    }
    
    public long getTimeMillisStart() {
    
        return timeMillisStart;
    }
    
    public long getTimeMillisEnd() {
    
        return timeMillisEnd;
    }
    
    public double getValue() {
    
        return value;
    }
    
    public int getValuesSize() {
    
        return valuesSize;
    }
}
