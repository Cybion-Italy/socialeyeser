package it.cybion.socialeyeser.trends;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Filter.class);

    public static Observable<Integer> filterGtEq(final int limit,
            final Observable<Integer> perSecondSpeedometer) {

        return perSecondSpeedometer.filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer speed) {

                return speed >= limit;
            }
        }).observeOn(Schedulers.computation());
    }

}
