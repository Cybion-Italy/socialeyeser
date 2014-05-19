package it.cybion.socialeyeser.trends;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Filter.class);

    private Observable<Integer> perSecondSpeedometer;

    private final Scheduler scheduler;

    public Filter(final Observable<Integer> perSecondSpeedometer, Scheduler scheduler) {

        this.perSecondSpeedometer = perSecondSpeedometer;
        this.scheduler = scheduler;
    }

    public Observable<Integer> filterGtEq(final int limit) {

        return perSecondSpeedometer.filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(final Integer speed) {

                return speed >= limit;
            }
        }).observeOn(this.scheduler);
    }

}
