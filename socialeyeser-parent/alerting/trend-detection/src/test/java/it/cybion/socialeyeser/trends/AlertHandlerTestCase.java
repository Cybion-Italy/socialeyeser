package it.cybion.socialeyeser.trends;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AlertHandlerTestCase {
    
    private static final double ALERT_RATIO_THRESHOLD = 0.1;
    private static final Logger LOGGER = LoggerFactory.getLogger(AlertHandlerTestCase.class);
    AlertHandler handler;
    Alert alert;
    Observer observer;
    
    @BeforeClass
    public void setup() {
    
        observer = EasyMock.createStrictMock(Observer.class);
        alert = new Alert(new DateTime(), 0.05, 0, new HashMap<String, Double>());
    }
    
    @Test
    public void shouldTestAlertHandler() throws Exception {
    
        reset(observer);
        observer.update(anyObject(Observable.class), anyObject(Alert.class));
        expectLastCall().andAnswer(new IAnswer() {
            
            public Object answer() {
            
                assertTrue(false);
                return null;
            }
        }).anyTimes();
        
        replay(observer);
        handler = new AlertHandler(ALERT_RATIO_THRESHOLD);
        handler.addObserver(observer);
        alert.setAlertLevel(0.2);
        handler.handle(alert);
        alert.setAlertLevel(0.205);
        handler.handle(alert);
        verify(observer);
        
        reset(observer);
        observer.update(anyObject(Observable.class), anyObject(Alert.class));
        expectLastCall().once();
        replay(observer);
        handler = new AlertHandler(ALERT_RATIO_THRESHOLD);
        handler.addObserver(observer);
        alert.setAlertLevel(0.2);
        handler.handle(alert);
        alert.setAlertLevel(0.25);
        handler.handle(alert);
        verify(observer);
        
        reset(observer);
        observer.update(anyObject(Observable.class), anyObject(Alert.class));
        expectLastCall().once();
        replay(observer);
        handler = new AlertHandler(ALERT_RATIO_THRESHOLD);
        handler.addObserver(observer);
        alert.setAlertLevel(0.2);
        handler.handle(alert);
        alert.setAlertLevel(0.5);
        handler.handle(alert);
        verify(observer);
    }
    
}
