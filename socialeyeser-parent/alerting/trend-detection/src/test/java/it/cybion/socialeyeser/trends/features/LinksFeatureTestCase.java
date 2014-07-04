package it.cybion.socialeyeser.trends.features;

import static org.testng.Assert.assertEquals;
import it.cybion.socialeyeser.trends.features.simple.count.LinksFeature;

import java.io.IOException;
import java.net.URISyntaxException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LinksFeatureTestCase extends AbstractFeatureTestCase {
    
    public LinksFeatureTestCase() throws IOException, URISyntaxException {
    
        super();
        
    }
    
    @BeforeClass
    public void setup() {
    
        feature = new LinksFeature();
    }
    
    @Test
    @Override
    public void shouldTestFeature() throws Exception {
    
        double value = feature.extractFrom(sampleTweet);
        assertEquals(value, 1.0);
    }
    
}
