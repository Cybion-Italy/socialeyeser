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
import it.cybion.socialeyeser.trends.features.TweetFeature;
import it.cybion.socialeyeser.trends.features.base.Feature;
import it.cybion.socialeyeser.trends.features.windows.FixedTimeWindow;
import it.cybion.socialeyeser.trends.model.Tweet;

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
    
    private static final long ONE_MINUTE_MILLIS = 60 * 1000L;
    private static final long ONE_HOUR_MILLIS = 60 * ONE_MINUTE_MILLIS;
    private static final long SIX_HOURS_MILLIS = 6 * ONE_HOUR_MILLIS;
    private static final long ONE_DAY_MILLIS = 24 * ONE_HOUR_MILLIS;
    
    private final double delta = 0.002;// default 0.002;
    private final Map<Feature, AdWin> featureObservers;
    private final Feature[] featureObserverList;
    
    public CrisisDetector() {
    
        featureObservers = new HashMap<Feature, AdWin>();
        
        featureObserverList = new Feature[] {
        
        new TweetFeature(new FixedTimeWindow(ONE_MINUTE_MILLIS)),
                new IsARetweetFeature(new FixedTimeWindow(ONE_MINUTE_MILLIS)),
                
                new FavoritesFeature(new FixedTimeWindow(ONE_HOUR_MILLIS)),
                new FollowersFeature(new FixedTimeWindow(ONE_HOUR_MILLIS)),
                new FollowingsFeature(new FixedTimeWindow(ONE_HOUR_MILLIS)),
                new HashtagsWindowFeature(new FixedTimeWindow(ONE_HOUR_MILLIS)),
                new LinksFeature(new FixedTimeWindow(ONE_HOUR_MILLIS)),
                new MentionsFeature(new FixedTimeWindow(ONE_HOUR_MILLIS)),
                new TweetFeature(new FixedTimeWindow(ONE_HOUR_MILLIS)),
                new IsARetweetFeature(new FixedTimeWindow(ONE_HOUR_MILLIS)),
                new RetweetsFeature(new FixedTimeWindow(ONE_HOUR_MILLIS)),
                
                new FavoritesFeature(new FixedTimeWindow(SIX_HOURS_MILLIS)),
                new FollowersFeature(new FixedTimeWindow(SIX_HOURS_MILLIS)),
                new FollowingsFeature(new FixedTimeWindow(SIX_HOURS_MILLIS)),
                new HashtagsWindowFeature(new FixedTimeWindow(SIX_HOURS_MILLIS)),
                new LinksFeature(new FixedTimeWindow(SIX_HOURS_MILLIS)),
                new MentionsFeature(new FixedTimeWindow(SIX_HOURS_MILLIS)),
                new TweetFeature(new FixedTimeWindow(SIX_HOURS_MILLIS)),
                new IsARetweetFeature(new FixedTimeWindow(SIX_HOURS_MILLIS)),
                new RetweetsFeature(new FixedTimeWindow(SIX_HOURS_MILLIS)),
                
                new FavoritesFeature(new FixedTimeWindow(ONE_DAY_MILLIS)),
                new FollowersFeature(new FixedTimeWindow(ONE_DAY_MILLIS)),
                new FollowingsFeature(new FixedTimeWindow(ONE_DAY_MILLIS)),
                new HashtagsWindowFeature(new FixedTimeWindow(ONE_DAY_MILLIS)),
                new LinksFeature(new FixedTimeWindow(ONE_DAY_MILLIS)),
                new MentionsFeature(new FixedTimeWindow(ONE_DAY_MILLIS)),
                new TweetFeature(new FixedTimeWindow(ONE_DAY_MILLIS)),
                new IsARetweetFeature(new FixedTimeWindow(ONE_DAY_MILLIS)),
                new RetweetsFeature(new FixedTimeWindow(ONE_DAY_MILLIS))
        
        };
        
        for (Feature feat : featureObserverList) {
            featureObservers.put(feat, new AdWin(delta));
        }
        
    }
    
    public void detect(Tweet tweet) {
    
        double activatedObservers = 0.00;
        double observerValue = 0.0;
        
        for (Feature observer : featureObserverList) {
            
            observerValue = observer.extractFrom(tweet);
            
            if (featureObservers.get(observer).setInput(observerValue)) {
                activatedObservers++;
            }
        }
        
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
