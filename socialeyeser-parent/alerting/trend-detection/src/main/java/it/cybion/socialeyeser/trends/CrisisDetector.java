package it.cybion.socialeyeser.trends;

import it.cybion.socialeyeser.trends.adwin.AdWin;
import it.cybion.socialeyeser.trends.features.FavoritesFeature;
import it.cybion.socialeyeser.trends.features.FollowersFeature;
import it.cybion.socialeyeser.trends.features.FollowingsFeature;
import it.cybion.socialeyeser.trends.features.HashtagsWindowFeature;
import it.cybion.socialeyeser.trends.features.IsARetweetFeature;
import it.cybion.socialeyeser.trends.features.LinksFeature;
import it.cybion.socialeyeser.trends.features.MentionsFeature;
import it.cybion.socialeyeser.trends.features.RetweetsFeature;
import it.cybion.socialeyeser.trends.features.TweetWindowFeature;
import it.cybion.socialeyeser.trends.features.base.Feature;
import it.cybion.socialeyeser.trends.features.windows.FixedTimeWindow;
import it.cybion.socialeyeser.trends.model.Tweet;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class CrisisDetector extends Observable {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CrisisDetector.class);
    
    private final double delta = 0.002;// default 0.002;
    Map<Feature, AdWin> featureObservers;
    Feature[] featureObserverList;
    PrintStream ps;
    
    public CrisisDetector(PrintStream ps) {
    
        this.ps = ps;
        
        featureObservers = new HashMap<Feature, AdWin>();
        // featureObserverList = new Feature[] { new FavoritesFeature(), new
        // FollowersFeature(),
        // new FollowingFeature(), new HashtagsFeature(), new LinksFeature(),
        // new MentionsFeature(), new RetweetsFeature(), new RetweetFrequency(),
        // new TweetFrequency() };
        // ps.println("timeMillis favorite favorite_alert follower follower_alert following following_alert hashtag hashtag_alert link link_alert mention mention_alert retweet retweet_alert retweet_freq retweet_freq_alert tweet_freq tweet_freq_alert total_alerts");
        
        long oneHourMillis = 3600 * 1000L;
        long sixHoursMillis = 6 * oneHourMillis;
        long oneDayMillis = 24 * oneHourMillis;
        featureObserverList = new Feature[] {
                new FavoritesFeature(new FixedTimeWindow(oneHourMillis)),
                new FollowersFeature(new FixedTimeWindow(oneHourMillis)),
                new FollowingsFeature(new FixedTimeWindow(oneHourMillis)),
                new HashtagsWindowFeature(new FixedTimeWindow(oneHourMillis)),
                new LinksFeature(new FixedTimeWindow(oneHourMillis)),
                new MentionsFeature(new FixedTimeWindow(oneHourMillis)),
                new TweetWindowFeature(new FixedTimeWindow(oneHourMillis)),
                new IsARetweetFeature(new FixedTimeWindow(oneHourMillis)),
                new RetweetsFeature(new FixedTimeWindow(oneHourMillis)),
                
                new FavoritesFeature(new FixedTimeWindow(sixHoursMillis)),
                new FollowersFeature(new FixedTimeWindow(sixHoursMillis)),
                new FollowingsFeature(new FixedTimeWindow(sixHoursMillis)),
                new HashtagsWindowFeature(new FixedTimeWindow(sixHoursMillis)),
                new LinksFeature(new FixedTimeWindow(sixHoursMillis)),
                new MentionsFeature(new FixedTimeWindow(sixHoursMillis)),
                new TweetWindowFeature(new FixedTimeWindow(sixHoursMillis)),
                new IsARetweetFeature(new FixedTimeWindow(sixHoursMillis)),
                new RetweetsFeature(new FixedTimeWindow(sixHoursMillis)),
                
                new FavoritesFeature(new FixedTimeWindow(oneDayMillis)),
                new FollowersFeature(new FixedTimeWindow(oneDayMillis)),
                new FollowingsFeature(new FixedTimeWindow(oneDayMillis)),
                new HashtagsWindowFeature(new FixedTimeWindow(oneDayMillis)),
                new LinksFeature(new FixedTimeWindow(oneDayMillis)),
                new MentionsFeature(new FixedTimeWindow(oneDayMillis)),
                new TweetWindowFeature(new FixedTimeWindow(oneDayMillis)),
                new IsARetweetFeature(new FixedTimeWindow(oneDayMillis)),
                new RetweetsFeature(new FixedTimeWindow(oneDayMillis))
        
        };
        ps.println("timeMillis favorite1h follower1h following1h hashtag1h link1h mention1h tweet1h retweet1h retweet_count1h favorite6h follower6h following6h hashtag6h link6h mention6h tweet6h retweet6h retweet_count6h favorite24h follower24h following24h hashtag24h link24h mention24h tweet24h retweet24h retweet_count24h total_alerts");
        
        for (Feature feat : featureObserverList) {
            featureObservers.put(feat, new AdWin(delta));
        }
        
    }
    
    public void detect(Tweet tweet) {
    
        double activatedObservers = 0.00;
        double observerValue = 0.0;
        
        ps.print(tweet.createdAt.getTime() + " ");
        
        for (Feature observer : featureObserverList) {
            
            observerValue = observer.extractFrom(tweet);
            ps.print(observerValue + " ");
            
            if (featureObservers.get(observer).setInput(observerValue)) {
                activatedObservers++;
            }
            
        }
        ps.print(activatedObservers + "\n");
        
        final boolean aCrisisIsHappening = checkIfCrisis(activatedObservers);
        if (aCrisisIsHappening) {
            notifyObservers("crisis!");
        }
        
    }
    
    public void add(final Observer anObserver) {
    
        this.addObserver(anObserver);
    }
    
    private boolean checkIfCrisis(double activatedObservers) {
    
        final double activatedObserversRatio = activatedObservers / featureObservers.size();
        return activatedObserversRatio >= 0.85D;
        
    }
}
