package it.cybion.socialeyeser.trends.features.count;

import static org.testng.Assert.assertEquals;
import it.cybion.socialeyeser.trends.features.AbstractFeatureTestCase;
import it.cybion.socialeyeser.trends.features.simple.count.HashtagsFeature;

import java.io.IOException;
import java.net.URISyntaxException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HashtagsFeatureTestCase extends AbstractFeatureTestCase {
    
    public HashtagsFeatureTestCase() throws IOException, URISyntaxException {
    
        super();
        
    }
    
    @BeforeClass
    public void setup() {
    
        feature = new HashtagsFeature();
    }
    
    @Test
    public void shouldTestFeature() throws Exception {
    
        double value = feature.extractFrom(sampleTweet);
        assertEquals(value, 1.0);
    }
    
}
