package it.cybion.socialeyeser.trends.runners;

import it.cybion.socialeyeser.trends.CrisisDetector;
import it.cybion.socialeyeser.trends.model.Tweet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

/*
 * an on-line trend miner for evaluating trends detection
 */
public class TrendsMiner {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TrendsMiner.class);
    
    private String applicationConsumerKey;
    private String applicationConsumerSecret;
    private String userKey;
    private String userSecret;
    
    CrisisDetector detector;
    
    PrintStream stats;
    ObjectMapper mapper;
    TwitterStream stream;
    long totalTweetCounter = 0;
    int tweetCounter = 0;
    static String[] query;
    double[][] locations = new double[][] { new double[] { 12.331, 41.782 },
            new double[] { 12.633, 42.005 } };
    
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
    
        String filterLanguage;
        String consumerKey, consumerSecret, userKey, userSecret, dataPath;
        
        if (args.length != 1) {
            System.out.println("parameters: configFile");
            return;
        }
        
        try {
            final Properties prop = new Properties();
            prop.load(new FileInputStream(new File(args[0])));
            
            consumerKey = (String) prop.get("consumerKey");
            consumerSecret = (String) prop.get("consumerSecret");
            userKey = (String) prop.get("userKey");
            userSecret = (String) prop.get("userSecret");
            
            query = ((String) prop.get("query")).split(",");
            
            if (consumerKey.length() == 0 || consumerSecret.length() == 0 || userKey.length() == 0
                    || userSecret.length() == 0)
                throw new Exception("config file contains some empty values");
            
        } catch (final Exception e) {
            System.out
                    .println("An error occurred while loading configuration. Please revise your params\n"
                            + e.getMessage());
            return;
        }
        
        TrendsMiner miner = new TrendsMiner(consumerKey, consumerSecret, userKey, userSecret);
        
        miner.fetchItems();
        
    }
    
    public TrendsMiner(String consumerKey, String consumerSecret, String userKey, String userSecret)
            throws FileNotFoundException {
    
        stats = new PrintStream(new File("/Data/alerts.txt"));
        
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.mapper
                .setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        this.mapper.setDateFormat(new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy"));
        
        this.applicationConsumerKey = consumerKey;
        this.applicationConsumerSecret = consumerSecret;
        this.userKey = userKey;
        this.userSecret = userSecret;
        
        registerShutdownHook();
        
        initDetector();
        
    }
    
    private void initDetector() {
    
        detector = new CrisisDetector(0.8, 0.3, 1000);
    }
    
    public void fetchItems() {
    
        final StatusListener statusListener = new StatusListener() {
            
            @Override
            public void onStatus(final Status status) {
            
                final String json = DataObjectFactory.getRawJSON(status);
                
                try {
                    
                    final Tweet tweet = mapper.readValue(json, Tweet.class);
                    
                    totalTweetCounter++;
                    detector.detect(tweet);
                    long confidence = 0;
                    if (confidence > 0)
                        stats.println(System.currentTimeMillis() + " " + confidence);
                    
                } catch (final JsonParseException e) {
                    LOGGER.error("Skipping unserializable tweet json");
                } catch (final JsonMappingException e) {
                    LOGGER.error("Skipping unserializable tweet json");
                } catch (final IOException e) {
                    LOGGER.error("Skipping unserializable tweet json");
                }
            }
            
            @Override
            public void onDeletionNotice(final StatusDeletionNotice statusDeletionNotice) {
            
            }
            
            @Override
            public void onTrackLimitationNotice(final int numberOfLimitedStatuses) {
            
            }
            
            @Override
            public void onException(final Exception ex) {
            
                ex.printStackTrace();
            }
            
            @Override
            public void onScrubGeo(final long arg0, final long arg1) {
            
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onStallWarning(final StallWarning stallWarning) {
            
                // TODO manage it
            }
        };
        
        final TwitterStreamFactory twitterFactory = new TwitterStreamFactory(getConfiguration());
        stream = twitterFactory.getInstance();
        stream.addListener(statusListener);
        final FilterQuery filterQuery = new FilterQuery();
        
        final StringBuilder logMessage = new StringBuilder();
        // logMessage.append("Filter query with tags: ");
        // String delimiter = "";
        // for (final String keyword : query) {
        // logMessage.append(delimiter + "'" + keyword + "'");
        // delimiter = ", ";
        // }
        logMessage
                .append(" with credentials:\nuserKey: " + userKey + "\nuserSecret: " + userSecret);
        LOGGER.info(logMessage.toString());
        filterQuery.track(query);
        // filterQuery.locations(locations);
        stream.filter(filterQuery);
    }
    
    private Configuration getConfiguration() {
    
        final ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setDebugEnabled(true).setJSONStoreEnabled(true)
                .setOAuthConsumerKey(applicationConsumerKey)
                .setOAuthConsumerSecret(applicationConsumerSecret).setOAuthAccessToken(userKey)
                .setOAuthAccessTokenSecret(userSecret);
        
        return configurationBuilder.build();
    }
    
    public void stopFetchingItems() {
    
        if (isActive()) {
            stream.shutdown();
            stream = null;
        }
        
    }
    
    public boolean isActive() {
    
        return stream != null;
    }
    
    private void registerShutdownHook() {
    
        Runtime.getRuntime().addShutdownHook(new Thread() {
            
            @Override
            public void run() {
            
                System.out.println("Closing structures...");
                
                try {
                    // stats.close();
                    System.out.println("Closing Stream...");
                    stream.shutdown();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
                
                System.out.println("Exiting!");
            }
        });
    }
    
}
