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
public class StringLengthOMeter {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringLengthOMeter.class);

    private final Observable<String> stringObservable;

    private final Scheduler io;

    public StringLengthOMeter(final Observable<String> observable, final Scheduler io) {

        this.stringObservable = observable;
        this.io = io;
    }

    public Observable<Integer> measureEvery(final int timespan, final TimeUnit timeunit) {

        return this.stringObservable.buffer(timespan, timeunit).map(
                new Func1<List<String>, Integer>() {
                    @Override
                    public Integer call(final List<String> strings) {

                        int totalLength = 0;
                        for (final String string : strings) {
                            totalLength += string.length();
                        }

                        return totalLength;
                    }
                }).observeOn(this.io);

    }
}
