package it.cybion.socialeyeser.trends;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public abstract class BaseAlertObserver implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        //check arg instanceof Alert
        //call local method to manage it
        handle((Alert) arg);

    }

    public abstract void handle(final Alert alert);
}
