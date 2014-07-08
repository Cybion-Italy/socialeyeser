package it.cybion.socialeyeser.trends;

import java.util.Map.Entry;
import java.util.Observable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlertHandler extends Observable {
    
    private static double ALERT_HANDLER_THRESHOLD = 0.3;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AlertHandler.class);
    
    // TODO hangle overflow on doubles...reset sums
    private Alert lastAlert;
    private double alertLevelSum = 0;
    private double alertsCount = 0;
    private double averageAlertLevel;
    
    public AlertHandler(double alertLevelRatioOnAverage) {
    
        this.ALERT_HANDLER_THRESHOLD = alertLevelRatioOnAverage;
        lastAlert = Alert.NULL;
        
    }
    
    public void handle(Alert alert) {
    
        if (!alert.equals(Alert.NULL)) {
            
            alertLevelSum += alert.getAlertLevel();
            alertsCount++;
            averageAlertLevel = alertLevelSum / alertsCount;
            
            boolean validAlert = Math.abs((alert.getAlertLevel() - averageAlertLevel)
                    / averageAlertLevel) > ALERT_HANDLER_THRESHOLD;
            
            if (validAlert) {
                printAlert(alert);
                
                setChanged();
                lastAlert = alert;
                notifyObservers(alert);
            }
            
        }
    }
    
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
