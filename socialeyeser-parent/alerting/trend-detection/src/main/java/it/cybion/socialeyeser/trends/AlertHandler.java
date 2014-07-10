package it.cybion.socialeyeser.trends;

import java.util.Map.Entry;
import java.util.Observable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlertHandler extends Observable {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AlertHandler.class);
    
    private final double alertHandlerThreshold;
    private final double minInterAlertTimeMillis;
    
    // TODO hangle overflow on doubles...reset sums
    private Alert lastAlert;
    private double alertLevelSum;
    private double alertsCount;
    private double averageAlertLevel;
    
    public AlertHandler() {
    
        this(0.3D, 3 * 3600 * 1000L);
    }
    
    public AlertHandler(double alertHandlerThreshold, double minInterAlertTimeMillis) {
    
        this(alertHandlerThreshold, minInterAlertTimeMillis, 0, 0, 0);
        
    }
    
    public AlertHandler(double alertHandlerThreshold, double minInterAlertTimeMillis,
            double alertLevelSum, double alertsCount, double averageAlertLevel) {
    
        this.alertHandlerThreshold = alertHandlerThreshold;
        this.minInterAlertTimeMillis = minInterAlertTimeMillis;
        this.lastAlert = Alert.NULL;
        this.alertLevelSum = alertLevelSum;
        this.alertsCount = alertsCount;
        this.averageAlertLevel = averageAlertLevel;
    }
    
    public void handle(Alert alert) {
    
        if (!alert.equals(Alert.NULL)) {
            
            alertLevelSum += alert.getAlertLevel();
            alertsCount++;
            averageAlertLevel = alertLevelSum / alertsCount;
            
            boolean validAlert = Math.abs((alert.getAlertLevel() - averageAlertLevel)
                    / averageAlertLevel) > alertHandlerThreshold;
            
            if (lastAlert != null) {
                long lastAlertPeriod = alert.getCreatedAt().getMillis()
                        - lastAlert.getCreatedAt().getMillis();
                if (lastAlertPeriod < minInterAlertTimeMillis)
                    validAlert = false;
            }
            
            if (validAlert) {
                printAlert(alert);
                
                setChanged();
                lastAlert = alert;
                notifyObservers(alert);
            }
            
        }
    }
    
    // can be static passing some more params in?
    private void printAlert(Alert alert) {
    
        LOGGER.debug("");
        LOGGER.debug("---------New Alert on " + alert.getCreatedAt() + "--------");
        LOGGER.debug("Alert Level: " + alert.getAlertLevel());
        LOGGER.debug("Average Alert Level: " + averageAlertLevel);
        LOGGER.debug("Observed Alerts: " + alertsCount);
        
        for (Entry<String, Double> entry : alert.getAlertFeatures().entrySet())
            LOGGER.debug(entry.getKey() + " -> " + entry.getValue());
        
    }
    
}
