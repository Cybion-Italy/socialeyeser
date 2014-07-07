package it.cybion.socialeyeser.trends;

import it.cybion.socialeyeser.trends.adwin.AdWin;
import it.cybion.socialeyeser.trends.features.Feature;
import it.cybion.socialeyeser.trends.features.sma.FavoritesWindowFeature;
import it.cybion.socialeyeser.trends.features.sma.FollowersWindowFeature;
import it.cybion.socialeyeser.trends.features.sma.FollowingsWindowFeature;
import it.cybion.socialeyeser.trends.features.sma.HashtagsCountWindowFeature;
import it.cybion.socialeyeser.trends.features.sma.LinksWindowFeature;
import it.cybion.socialeyeser.trends.features.sma.MentionsWindowFeature;
import it.cybion.socialeyeser.trends.features.sma.RetweetCountContainerFeature;
import it.cybion.socialeyeser.trends.features.sma.RetweetWindowFeature;
import it.cybion.socialeyeser.trends.features.sma.TweetWindowFeature;
import it.cybion.socialeyeser.trends.features.sma.windows.FixedTimeFeatureWindow;
import it.cybion.socialeyeser.trends.model.Tweet;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class CrisisDetector {
    
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
                new FavoritesWindowFeature(new FixedTimeFeatureWindow(oneHourMillis)),
                new FollowersWindowFeature(new FixedTimeFeatureWindow(oneHourMillis)),
                new FollowingsWindowFeature(new FixedTimeFeatureWindow(oneHourMillis)),
                new HashtagsCountWindowFeature(new FixedTimeFeatureWindow(oneHourMillis)),
                new LinksWindowFeature(new FixedTimeFeatureWindow(oneHourMillis)),
                new MentionsWindowFeature(new FixedTimeFeatureWindow(oneHourMillis)),
                new TweetWindowFeature(new FixedTimeFeatureWindow(oneHourMillis)),
                new RetweetWindowFeature(new FixedTimeFeatureWindow(oneHourMillis)),
                new RetweetCountContainerFeature(new FixedTimeFeatureWindow(oneHourMillis)),
                
                new FavoritesWindowFeature(new FixedTimeFeatureWindow(sixHoursMillis)),
                new FollowersWindowFeature(new FixedTimeFeatureWindow(sixHoursMillis)),
                new FollowingsWindowFeature(new FixedTimeFeatureWindow(sixHoursMillis)),
                new HashtagsCountWindowFeature(new FixedTimeFeatureWindow(sixHoursMillis)),
                new LinksWindowFeature(new FixedTimeFeatureWindow(sixHoursMillis)),
                new MentionsWindowFeature(new FixedTimeFeatureWindow(sixHoursMillis)),
                new TweetWindowFeature(new FixedTimeFeatureWindow(sixHoursMillis)),
                new RetweetWindowFeature(new FixedTimeFeatureWindow(sixHoursMillis)),
                new RetweetCountContainerFeature(new FixedTimeFeatureWindow(sixHoursMillis)),
                
                new FavoritesWindowFeature(new FixedTimeFeatureWindow(oneDayMillis)),
                new FollowersWindowFeature(new FixedTimeFeatureWindow(oneDayMillis)),
                new FollowingsWindowFeature(new FixedTimeFeatureWindow(oneDayMillis)),
                new HashtagsCountWindowFeature(new FixedTimeFeatureWindow(oneDayMillis)),
                new LinksWindowFeature(new FixedTimeFeatureWindow(oneDayMillis)),
                new MentionsWindowFeature(new FixedTimeFeatureWindow(oneDayMillis)),
                new TweetWindowFeature(new FixedTimeFeatureWindow(oneDayMillis)),
                new RetweetWindowFeature(new FixedTimeFeatureWindow(oneDayMillis)),
                new RetweetCountContainerFeature(new FixedTimeFeatureWindow(oneDayMillis))
        
        };
        ps.println("timeMillis favorite1h follower1h following1h hashtag1h link1h mention1h tweet1h retweet1h retweet_count1h favorite6h follower6h following6h hashtag6h link6h mention6h tweet6h retweet6h retweet_count6h favorite24h follower24h following24h hashtag24h link24h mention24h tweet24h retweet24h retweet_count24h total_alerts");
        
        for (Feature feat : featureObserverList)
            featureObservers.put(feat, new AdWin(delta));
        
    }
    
    public double detect(Tweet tweet) {
    
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
        
        return activatedObservers / featureObservers.size();
    }
}
