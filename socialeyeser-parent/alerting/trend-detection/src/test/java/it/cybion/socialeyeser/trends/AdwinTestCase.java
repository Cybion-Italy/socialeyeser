package it.cybion.socialeyeser.trends;

import it.cybion.socialeyeser.trends.adwin.AdWin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class AdwinTestCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AdwinTestCase.class);
    
    @Test
    public void stepFunction() {
    
        AdWin adwin = new AdWin(.01); // Init Adwin with delta=.01
        double input = 0;
        
        for (int i = 0; i < 1000; i++) {
            
            input = (i < 500 ? 1000 : 500);
            
            if (adwin.setInput(input))
                LOGGER.info("Change Detected: " + i);
        }
        // Get information from Adwin
        LOGGER.info("Mean:" + adwin.getEstimation());
        LOGGER.info("Variance:" + adwin.getVariance());
        LOGGER.info("Stand. dev:" + Math.sqrt(adwin.getVariance()));
        LOGGER.info("Width:" + adwin.getWidth());
        LOGGER.info("\n\n");
        
    }
    
    @Test
    public void noisyStepFunction() {
    
        AdWin adwin = new AdWin(.01); // Init Adwin with delta=.01
        double input = 0;
        
        for (int i = 0; i < 1000; i++) {
            
            if (i < 500)
                input = 500 + ((System.nanoTime() % 1000) / 1000.0);
            else
                input = 1000 + ((System.nanoTime() % 1000) / 1000.0);
            
            if (adwin.setInput(input))
                LOGGER.info("Change Detected: " + i);
        }
        // Get information from Adwin
        LOGGER.info("Mean:" + adwin.getEstimation());
        LOGGER.info("Variance:" + adwin.getVariance());
        LOGGER.info("Stand. dev:" + Math.sqrt(adwin.getVariance()));
        LOGGER.info("Width:" + adwin.getWidth());
        LOGGER.info("\n\n");
        
    }
    
    @Test
    public void sinFunction() {
    
        AdWin adwin = new AdWin(.00001); // Init Adwin with delta=.01
        double input = 0;
        
        for (int i = 0; i < 1000; i++) {
            
            input = Math.sin(i / 100.0);
            
            if (adwin.setInput(input))
                LOGGER.info("Change Detected: " + i);
        }
        // Get information from Adwin
        LOGGER.info("Mean:" + adwin.getEstimation());
        LOGGER.info("Variance:" + adwin.getVariance());
        LOGGER.info("Stand. dev:" + Math.sqrt(adwin.getVariance()));
        LOGGER.info("Width:" + adwin.getWidth());
        LOGGER.info("\n\n");
        
    }
    
}
