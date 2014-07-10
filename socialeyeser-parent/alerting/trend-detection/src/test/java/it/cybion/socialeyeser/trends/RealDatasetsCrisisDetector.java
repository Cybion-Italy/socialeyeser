package it.cybion.socialeyeser.trends;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import it.cybion.socialeyeser.trends.runners.StreamReplayer;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RealDatasetsCrisisDetector {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RealDatasetsCrisisDetector.class);
    
    private static final String DATASETS_DIR = "/Data/crisis_data/ordered/";
    // private static final String[] DATASET_NAMES = new String[] { "fiat" };
    
    private Observer mockObserver;
    
    private static final String[] DATASET_NAMES = new String[] { "fiat", "toyota", "prandelli",
            "unipolsai", "ubibanca", "carige", "mcdonalds" };
    
    @BeforeClass
    public void setUp() {
    
        this.mockObserver = createStrictMock(Observer.class);
    }
    
    @Test(enabled = false)
    public void shouldTestPossibleThresholds() throws FileNotFoundException {
    
        PrintStream ps = new PrintStream("explore.txt");
        
        for (double alertThreshold = 0.6; alertThreshold > 0; alertThreshold -= 0.1) {
            
            for (double alertRatioThreshold = (1.0 - alertThreshold) / alertThreshold; alertRatioThreshold > 0; alertRatioThreshold -= 0.1) {
                
                try {
                    ps.print(alertThreshold + " " + alertRatioThreshold + " ");
                    testCrisisDetectorOnRealDatasets(alertThreshold, alertRatioThreshold);
                    ps.print(" 1\n");
                } catch (AssertionError e) {
                    LOGGER.error("Invalid parameters: " + alertThreshold + " "
                            + alertRatioThreshold);
                    ps.print(" -1\n");
                }
            }
        }
    }
    
    public void testCrisisDetectorOnRealDatasets(double alertThreshold, double alertRatioThreshold) {
    
        for (String crisisName : DATASET_NAMES) {
            
            LOGGER.info("Testing " + crisisName);
            
            StreamReplayer replayer = new StreamReplayer(3000000, DATASETS_DIR + crisisName
                    + ".zip");
            
            reset(this.mockObserver);
            mockObserver.update(anyObject(Observable.class), anyObject());
            expectLastCall().atLeastOnce();
            replay(this.mockObserver);
            
            CrisisDetector detector = new CrisisDetector(alertThreshold, alertRatioThreshold);
            detector.add(mockObserver);
            
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
