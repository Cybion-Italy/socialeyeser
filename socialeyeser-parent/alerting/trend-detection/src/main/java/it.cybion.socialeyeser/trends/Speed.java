package it.cybion.socialeyeser.trends;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.functions.Func1;
import rx.observables.MathObservable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class Speed {

    private static final Logger LOGGER = LoggerFactory.getLogger(Speed.class);

    public static Observable<Integer> speedometer(final PublishSubject<String> consoleInputLines,
            final int bufferSizeSecs) {

        return consoleInputLines.asObservable().buffer(bufferSizeSecs, TimeUnit.SECONDS).map(
                new Func1<List<String>, Integer>() {
                    @Override
                    public Integer call(final List<String> strings) {

                        return strings.size();
                    }
                }
        ).observeOn(Schedulers.io());
    }

    public static Observable<Integer> movingAverageOf(final int windowSize,
            final Observable<Integer> perSecondSpeedometer) {

        return perSecondSpeedometer.window(windowSize).flatMap(
                new Func1<Observable<Integer>, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Observable<Integer> window) {

                        return MathObservable.averageInteger(window);

                    }
                }
        ).observeOn(Schedulers.computation());
    }
}
