package it.cybion.socialeyeser.trends;

import java.util.Observable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlertHandler extends Observable {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AlertHandler.class);
    
    private Alert lastAlert;
    
    public AlertHandler() {
    
        lastAlert = Alert.NULL;
        
    }
    
    public void handle(Alert alert) {
    
        if (!alert.equals(Alert.NULL)) {
            
            setChanged();
            lastAlert = alert;
            notifyObservers(alert);
        }
    }
    
}
