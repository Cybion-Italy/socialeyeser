package it.cybion.socialeyeser.trends;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.easymock.IAnswer;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class AlertHandlerTestCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AlertHandlerTestCase.class);
    private static final double ALERT_RATIO_THRESHOLD = 0.1;
    
    @Test(enabled = true)
    public void givenAValidAlertShouldNotifyTheObserver() throws Exception {
    
        final AlertHandler alertHandler = new AlertHandler(ALERT_RATIO_THRESHOLD, 1, 200, 1000, 0.2);
        final Observer mockObserver = createStrictMock(Observer.class);
        reset(mockObserver);
        setupUpdateIsCalledAtLeastOnce(mockObserver);
        replay(mockObserver);
        
        alertHandler.addObserver(mockObserver);
        final Map<String, Double> alertFeatures = new HashMap<String, Double>();
        alertFeatures.put("some key", 1.0D);
        final Alert someAlert = new Alert(new DateTime(), 0.23, 10, alertFeatures);
        
        alertHandler.handle(someAlert);
        verify(mockObserver);
        
    }
    
    @Test(enabled = true)
    public void given2ValidAlertShouldNotifyTheObserverOnce() throws Exception {
    
        final AlertHandler alertHandler = new AlertHandler(ALERT_RATIO_THRESHOLD, 1000, 200, 1, 0.2);
        final Observer mockObserver = createStrictMock(Observer.class);
        reset(mockObserver);
        setupUpObserverIsNotifiedOnce(mockObserver);
        replay(mockObserver);
        
        alertHandler.addObserver(mockObserver);
        final Map<String, Double> alertFeatures = new HashMap<String, Double>();
        alertFeatures.put("some key", 1.0D);
        final Alert someAlert = new Alert(new DateTime(), 0.23, 10, alertFeatures);
        
        alertHandler.handle(someAlert);
        alertHandler.handle(someAlert);
        verify(mockObserver);
        
    }
    
    @Test(enabled = true)
    public void givenANotValidAlertShouldNotNotifyTheObserver() throws Exception {
    
        final AlertHandler alertHandler = new AlertHandler(ALERT_RATIO_THRESHOLD, 1, 200, 1000, 0.2);
        final Observer mockObserver = createStrictMock(Observer.class);
        reset(mockObserver);
        setupUpdateIsCalledThenFails(mockObserver);
        replay(mockObserver);
        
        alertHandler.addObserver(mockObserver);
        final Map<String, Double> alertFeatures = new HashMap<String, Double>();
        alertFeatures.put("some key", 1.0D);
        final Alert someAlert = new Alert(new DateTime(), 0.21, 10, alertFeatures);
        
        alertHandler.handle(someAlert);
        try {
            verify(mockObserver);
            assertTrue(false);
        } catch (AssertionError e) {
            assertTrue(true);
        }
        
    }
    
    private void setupUpdateIsCalledAtLeastOnce(Observer mockObserver) {
    
        mockObserver.update(anyObject(Observable.class), anyObject());
        expectLastCall().atLeastOnce();
    }
    
    private void setupUpObserverIsNotifiedOnce(Observer mockObserver) {
    
        mockObserver.update(anyObject(Observable.class), anyObject());
        expectLastCall().once();
        
    }
    
    private void setupUpdateIsCalledThenFails(Observer mockObserver) {
    
        mockObserver.update(anyObject(Observable.class), anyObject());
        expectLastCall().andAnswer(new IAnswer<Object>() {
            
            @Override
            public Object answer() throws Throwable {
            
                fail();
                return null;
            }
        });
        
    }
    
}
