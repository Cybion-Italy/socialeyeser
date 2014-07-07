package it.cybion.socialeyeser.trends;

import java.util.Observable;
import java.util.Observer;

public class DummyObserver implements Observer {
    
    Alert alert;
    
    @Override
    public void update(Observable o, Object arg) {
    
        this.alert = (Alert) arg;
        System.out.println(alert.getAlertLevel());
        
    }
    
}
