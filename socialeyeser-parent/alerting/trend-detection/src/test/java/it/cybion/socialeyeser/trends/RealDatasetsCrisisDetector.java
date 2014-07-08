package it.cybion.socialeyeser.trends;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import it.cybion.socialeyeser.trends.runners.StreamReplayer;

import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RealDatasetsCrisisDetector {
    
    private static final double ALERT_RATIO_THRESHOLD = 0.1;
    
    private static final double ALERT_THRESHOLD = 0.75;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RealDatasetsCrisisDetector.class);
    
    private static final String DATASETS_DIR = "/Data/crisis_data/ordered/";
    private static final String[] DATASET_NAMES = new String[] { "fiat" };
    
    private Observer mockObserver;
    
    // private static final String[] DATASET_NAMES = new String[] { "fiat",
    // "toyota", "prandelli",
    // "unipolsai", "ubibanca", "carige", "mcdonalds" };
    
    @BeforeClass
    public void setUp() {
    
        this.mockObserver = createStrictMock(Observer.class);
    }
    
    @Test
    public void shouldTestCrisisDetectorOnRealDatasets() throws Exception {
    
        for (String crisisName : DATASET_NAMES) {
            
            LOGGER.info("Testing " + crisisName);
            
            StreamReplayer replayer = new StreamReplayer(3000000, DATASETS_DIR + crisisName
                    + ".zip");
            
            reset(this.mockObserver);
            mockObserver.update(anyObject(Observable.class), anyObject());
            expectLastCall().atLeastOnce();
            replay(this.mockObserver);
            
            CrisisDetector detector = new CrisisDetector(ALERT_THRESHOLD, ALERT_RATIO_THRESHOLD);
            
            LOGGER.debug("*** BEGIN OF STREAM for " + crisisName + " ***");
            try {
                while (true) {
                    detector.detect(replayer.nextTweet());
                }
            } catch (Exception e) {
                LOGGER.debug("*** END OF STREAM ***");
            }
            
            verify(this.mockObserver);
        }
    }
    
}
