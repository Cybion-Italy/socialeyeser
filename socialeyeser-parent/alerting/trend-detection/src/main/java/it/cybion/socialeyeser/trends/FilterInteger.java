package it.cybion.socialeyeser.trends;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class FilterInteger {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterInteger.class);

    private Observable<Integer> integerObservable;

    private final Scheduler scheduler;

    public FilterInteger(final Observable<Integer> integerObservable, final Scheduler scheduler) {

        this.integerObservable = integerObservable;
        this.scheduler = scheduler;
    }

    public Observable<Integer> filterGtEq(final int limit) {

        return integerObservable.filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(final Integer value) {

                return value >= limit;
            }
        }).observeOn(this.scheduler);
    }

}
