package it.cybion.socialeyeser.trends.features.freq;

import static org.testng.Assert.assertTrue;
import it.cybion.socialeyeser.trends.features.count.AbstractFeatureTestCase;
import it.cybion.socialeyeser.trends.features.simple.freq.RetweetFrequency;

import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RetweetFrequencyTestCase extends AbstractFeatureTestCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RetweetFrequencyTestCase.class);
    
    public RetweetFrequencyTestCase() throws IOException, URISyntaxException {
    
        super();
        
    }
    
    @BeforeClass
    public void setup() {
    
        feature = new RetweetFrequency();
    }
    
    @Test
    @Override
    public void shouldTestFeature() throws Exception {
    
        double value = feature.extractFrom(sampleTweet);
        LOGGER.info("punctual frequency: " + value);
        
        for (int i = 0; i < 100; i++) {
            
            value = feature.extractFrom(sampleTweet);
            LOGGER.info("punctual frequency: " + value + " / s");
            
            assertTrue(value > 80);
            Thread.sleep(10L);
            
        }
    }
    
}
