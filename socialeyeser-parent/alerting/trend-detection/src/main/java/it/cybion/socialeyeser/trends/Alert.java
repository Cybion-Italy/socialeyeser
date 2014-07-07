package it.cybion.socialeyeser.trends;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

public class Alert {
    
    public static final Alert NULL = new Alert();
    
    private DateTime createdAt;
    private double alertLevel;
    private int alertCount;
    private Map<String, Double> alertFeatures;
    
    private Alert() {
    
        this(new DateTime(), -1, -1, new HashMap<String, Double>());
    }
    
    public Alert(DateTime createdAt, double alertLevel, int alertCount,
            Map<String, Double> alertFeatures) {
    
        this.createdAt = createdAt;
        this.alertLevel = alertLevel;
        this.alertCount = alertCount;
        this.alertFeatures = alertFeatures;
    }
    
    public DateTime getCreatedAt() {
    
        return createdAt;
    }
    
    public double getAlertLevel() {
    
        return alertLevel;
    }
    
    public int getAlertCount() {
    
        return alertCount;
    }
    
    public Map<String, Double> getAlertFeatures() {
    
        return alertFeatures;
    }
    
    public void setCreatedAt(DateTime createdAt) {
    
        this.createdAt = createdAt;
    }
    
    public void setAlertLevel(double alertLevel) {
    
        this.alertLevel = alertLevel;
    }
    
    public void setAlertCount(int alertCount) {
    
        this.alertCount = alertCount;
    }
    
    public void setAlertFeatures(Map<String, Double> alertFeatures) {
    
        this.alertFeatures = alertFeatures;
    }
    
    @Override
    public int hashCode() {
    
        final int prime = 31;
        int result = 1;
        result = prime * result + alertCount;
        result = prime * result + ((alertFeatures == null) ? 0 : alertFeatures.hashCode());
        long temp;
        temp = Double.doubleToLongBits(alertLevel);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
    
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Alert other = (Alert) obj;
        if (alertCount != other.alertCount)
            return false;
        if (alertFeatures == null) {
            if (other.alertFeatures != null)
                return false;
        } else if (!alertFeatures.equals(other.alertFeatures))
            return false;
        if (Double.doubleToLongBits(alertLevel) != Double.doubleToLongBits(other.alertLevel))
            return false;
        if (createdAt == null) {
            if (other.createdAt != null)
                return false;
        } else if (!createdAt.equals(other.createdAt))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
    
        return "Alert [createdAt=" + createdAt + ", alertLevel=" + alertLevel + ", alertCount="
                + alertCount + ", alertFeatures=" + alertFeatures + "]";
    }
    
}
