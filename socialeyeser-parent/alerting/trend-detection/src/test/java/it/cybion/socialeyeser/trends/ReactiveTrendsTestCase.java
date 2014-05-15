package it.cybion.socialeyeser.trends;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.MathObservable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ReactiveTrendsTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveTrendsTestCase.class);

    @Test
    public void shouldDetectSpeedChangesAndAvgsFromConsoleInput() throws Exception {

        final PublishSubject<String> consoleInputLines = PublishSubject.create();

        final Observable<Integer> currentSpeed = speedometerOf(consoleInputLines);

        final Observable<Integer> averageSpeed = movingAverageOf(5, currentSpeed);

        final Observable<Integer> limiter = filterGtEq(4, currentSpeed);

        limiter.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

                LOGGER.info("gteq 4 lps: " + integer);
            }
        });

        averageSpeed.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

                LOGGER.info("avg speed of last 5 seconds: " + integer);
            }
        });

        final InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferRead = new BufferedReader(inputStreamReader);

        String currentLine = "";
        int counter = 0;

        while (counter < 1000 && !currentLine.equals("stop")) {
            currentLine = bufferRead.readLine();
            consoleInputLines.onNext(currentLine);
            counter++;
        }

        consoleInputLines.onCompleted();


    }

    private Observable<Integer> filterGtEq(final int limit,
            final Observable<Integer> perSecondSpeedometer) {

        return perSecondSpeedometer.filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer speed) {

                return speed >= limit;
            }
        }).observeOn(Schedulers.computation());
    }

    private Observable<Integer> movingAverageOf(final int windowSize,
            final Observable<Integer> perSecondSpeedometer) {

        return perSecondSpeedometer
                .window(windowSize)
                .flatMap(new Func1<Observable<Integer>, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Observable<Integer> window) {

                        return MathObservable.averageInteger(window);

                    }
                }).observeOn(Schedulers.computation());
    }

    private Observable<Integer> speedometerOf(final PublishSubject<String> consoleInputLines) {

        return consoleInputLines.asObservable()
                .buffer(1, TimeUnit.SECONDS)
                .map(new Func1<List<String>, Integer>() {
                    @Override
                    public Integer call(final List<String> strings) {

                        return strings.size();
                    }
                }).observeOn(Schedulers.io());
    }
}
