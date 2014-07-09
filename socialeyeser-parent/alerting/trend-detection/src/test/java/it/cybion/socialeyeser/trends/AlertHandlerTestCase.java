package it.cybion.socialeyeser.trends;

import org.easymock.IAnswer;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.testng.Assert.fail;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class AlertHandlerTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlertHandlerTestCase.class);

    @Test(enabled = false)
    public void givenAValidAlertShouldNotifyTheObserver() throws Exception {

        final AlertHandler alertHandler = new AlertHandler();
        final Observer mockObserver = createStrictMock(Observer.class);
        reset(mockObserver);
        setupUpdateIsCalledAtLeastOnce(mockObserver);
        replay(mockObserver);

        alertHandler.addObserver(mockObserver);
        final Map<String, Double> alertFeatures = new HashMap<String, Double>();
        alertFeatures.put("some key", 1.0D);
        final Alert someAlert = new Alert(new DateTime(), 10.0D, 1, alertFeatures);
        alertHandler.handle(someAlert);
        verify(mockObserver);

    }

    @Test(enabled = false)
    public void givenANotValidAlertShouldNotNotifyTheObserver() throws Exception {

        final AlertHandler alertHandler = new AlertHandler();
        final Observer mockObserver = createStrictMock(Observer.class);
        reset(mockObserver);
        setupUpdateIsCalledThenFails(mockObserver);
        replay(mockObserver);

        alertHandler.addObserver(mockObserver);
        final Map<String, Double> alertFeatures = new HashMap<String, Double>();
        alertFeatures.put("some key", 1.0D);
        final Alert someAlert = new Alert(new DateTime(), 10.0D, 1, alertFeatures);
        alertHandler.handle(someAlert);
        verify(mockObserver);

    }

    private void setupUpdateIsCalledAtLeastOnce(Observer mockObserver) {

        mockObserver.update(anyObject(Observable.class), anyObject());
        expectLastCall().atLeastOnce();
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
