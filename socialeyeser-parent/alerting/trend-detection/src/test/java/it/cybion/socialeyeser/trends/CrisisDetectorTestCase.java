package it.cybion.socialeyeser.trends;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import it.cybion.socialeyeser.trends.model.HashTag;
import it.cybion.socialeyeser.trends.model.Tweet;
import it.cybion.socialeyeser.trends.model.Url;
import it.cybion.socialeyeser.trends.model.UserMention;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class CrisisDetectorTestCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CrisisDetectorTestCase.class);
    
    private static final String SAMPLE_TWEET_FILENAME = "/sample_tweet.json";
    private static final int STREAM_TWEETS_NUMBER = 100000;
    
    private Random rand;
    private CrisisDetector crisisDetector;
    private Tweet sampleTweet;
    private Observer aMockObserver;
    
    @BeforeClass
    public void setUp() throws Exception {
    
        this.rand = new Random();
        this.crisisDetector = new CrisisDetector(0.3, 0.3);
        this.aMockObserver = createStrictMock(Observer.class);
        this.crisisDetector.add(aMockObserver);
        this.sampleTweet = buildSampleTweet();
        
    }
    
    @AfterClass
    public void tearDown() throws Exception {
    
        crisisDetector = null;
    }
    
    /*
     * Simulates an increased activity in the 2/3 of the stream by increasing
     * all observed feature
     */
    @Test
    public void testCrisisDetectorAlerts() throws Exception {
    
        reset(this.aMockObserver);
        setup();
        replay(this.aMockObserver);
        
        Tweet tweet;
        
        LOGGER.info("********************* BEGIN OF STREAM **********************");
        for (int i = 0; i < STREAM_TWEETS_NUMBER / 3.0; i++) {
            
            if (i < STREAM_TWEETS_NUMBER / 3.0 || i > STREAM_TWEETS_NUMBER * 0.66)
                tweet = getStreamTweet(getRandInt() % 1500L, getRandInt() % 1000,
                        getRandInt() % 1000, getRandInt() % 50, getRandInt() % 50,
                        getRandInt() % 2, getRandInt() % 3, getRandInt() % 1);
            else
                tweet = getStreamTweet(getRandInt() % 500L, getRandInt() % 10000,
                        getRandInt() % 10000, getRandInt() % 500, getRandInt() % 500,
                        getRandInt() % 6, getRandInt() % 10, getRandInt() % 3);
            
            crisisDetector.detect(tweet);
            
        }
        LOGGER.info("********************* END OF STREAM **********************");
        
        verify(this.aMockObserver);
    }
    
    private void setup() {
    
        this.aMockObserver.update(anyObject(Observable.class), anyObject());
        expectLastCall().atLeastOnce();
        
    }
    
    private Tweet getStreamTweet(long incrementalTime, int followers, int following,
            int favoriteCount, int retweetCount, int hashtagsCount, int mentionsCount, int urlsCount) {
    
        sampleTweet.createdAt = new Date(sampleTweet.createdAt.getTime() + incrementalTime);
        
        List<HashTag> hashtags = new ArrayList<HashTag>();
        List<Url> urls = new ArrayList<Url>();
        List<UserMention> mentions = new ArrayList<UserMention>();
        
        for (int i = 0; i < hashtagsCount; i++) {
            hashtags.add(new HashTag());
        }
        
        for (int i = 0; i < urlsCount; i++) {
            urls.add(new Url());
        }
        
        for (int i = 0; i < mentionsCount; i++) {
            mentions.add(new UserMention());
        }
        
        sampleTweet.user.followersCount = followers;
        sampleTweet.user.friendsCount = following;
        sampleTweet.entities.urls = urls.toArray(new Url[urls.size()]);
        sampleTweet.entities.hashtags = hashtags.toArray(new HashTag[hashtags.size()]);
        sampleTweet.entities.userMentions = mentions.toArray(new UserMention[mentions.size()]);
        sampleTweet.retweetedStatus.favoriteCount = favoriteCount;
        sampleTweet.retweetedStatus.retweetCount = retweetCount;
        
        return sampleTweet;
    }
    
    private Tweet buildSampleTweet() throws URISyntaxException, IOException {
    
        final FileInputStream stream = new FileInputStream(new File(this.getClass()
                .getResource(SAMPLE_TWEET_FILENAME).toURI()));
        try {
            final FileChannel fc = stream.getChannel();
            final MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            String json = Charset.defaultCharset().decode(bb).toString();
            
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper
                    .setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
            objectMapper.setDateFormat(new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy"));
            
            Tweet sampleTweet = objectMapper.readValue(json, Tweet.class);
            return sampleTweet;
        } finally {
            stream.close();
        }
    }
    
    private int getRandInt() {
    
        int randomValue = rand.nextInt();
        if (randomValue < 0)
            randomValue *= -1;
        
        return randomValue;
    }
}
