package it.cybion.socialeyeser.trends.features.windows;

import static org.testng.Assert.assertEquals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class WindowTestCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(WindowTestCase.class);
    
    private long windowLengthMillis;
    private FixedTimeWindow window;
    
    @Test
    public void shouldTestFixedTimeWindowHumanReadableRepresentation() {
    
        windowLengthMillis = 800L;
        window = new FixedTimeWindow(windowLengthMillis);
        LOGGER.info(windowLengthMillis + " ms -> " + window.getHumanReadableWindowLength());
        assertEquals(window.getHumanReadableWindowLength(), "0.8 s");
        
        windowLengthMillis = 1800L;
        window = new FixedTimeWindow(windowLengthMillis);
        LOGGER.info(windowLengthMillis + " ms -> " + window.getHumanReadableWindowLength());
        assertEquals(window.getHumanReadableWindowLength(), "1.8 s");
        
        windowLengthMillis = 71000L;
        window = new FixedTimeWindow(windowLengthMillis);
        LOGGER.info(windowLengthMillis + " ms -> " + window.getHumanReadableWindowLength());
        assertEquals(window.getHumanReadableWindowLength(), "1.2 m");
        
        windowLengthMillis = 9000000L;
        window = new FixedTimeWindow(windowLengthMillis);
        LOGGER.info(windowLengthMillis + " ms -> " + window.getHumanReadableWindowLength());
        assertEquals(window.getHumanReadableWindowLength(), "2.5 h");
        
        windowLengthMillis = 172800000L;
        window = new FixedTimeWindow(windowLengthMillis);
        LOGGER.info(windowLengthMillis + " ms -> " + window.getHumanReadableWindowLength());
        assertEquals(window.getHumanReadableWindowLength(), "2.0 d");
        
    }
}
