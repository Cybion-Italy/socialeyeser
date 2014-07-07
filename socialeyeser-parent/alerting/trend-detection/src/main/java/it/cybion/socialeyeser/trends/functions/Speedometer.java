package it.cybion.socialeyeser.trends.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class Speedometer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Speedometer.class);

    private Observable<T> observable;

    private Scheduler scheduler;

    public Speedometer(final Observable<T> observable, final Scheduler scheduler) {

        this.observable = observable;
        this.scheduler = scheduler;
    }

    public Observable<Integer> measureEvery(final int timespan, final TimeUnit unit) {

        return this.observable.buffer(timespan, unit).map(
                new Func1<List<T>, Integer>() {
                    @Override
                    public Integer call(final List<T> strings) {

                        return strings.size();
                    }
                }
        ).observeOn(this.scheduler);
    }
}
