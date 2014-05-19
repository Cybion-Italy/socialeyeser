package it.cybion.socialeyeser.trends;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.observables.MathObservable;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class Average {

    private static final Logger LOGGER = LoggerFactory.getLogger(Average.class);

    private Scheduler scheduler;

    private Observable<Integer> observable;

    public Average(final Observable<Integer> observable, final Scheduler scheduler) {

        this.scheduler = scheduler;
        this.observable = observable;
    }

    public Observable<Integer> movingAverageOf(final int windowSize) {

        return this.observable.window(windowSize).flatMap(
                new Func1<Observable<Integer>, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(final Observable<Integer> window) {

                        return MathObservable.averageInteger(window);

                    }
                }
        ).observeOn(this.scheduler);
    }
}
