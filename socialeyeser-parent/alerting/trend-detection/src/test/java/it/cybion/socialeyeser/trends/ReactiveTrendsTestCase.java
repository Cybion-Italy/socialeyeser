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

    @Test(enabled = true)
    public void shouldDetectSpeedChangesAndAvgsFromConsoleInput() throws Exception {

        //input
        final PublishSubject<String> consoleInputLines = PublishSubject.create();

        //build observables
        final Observable<Integer> currentSpeed = speedometerOf(consoleInputLines, 1);

        final Observable<Integer> averageSpeed = movingAverageOf(5, currentSpeed);

        final Observable<Integer> limiter = filterGtEq(5, currentSpeed);

        //subscribe loggers
        limiter.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

                LOGGER.info("gteq 5 lps: " + integer);
            }
        });

        averageSpeed.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

                LOGGER.info("avg speed of last 5 seconds: " + integer);
            }
        });

        //read from System.in
        final InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        final BufferedReader bufferRead = new BufferedReader(inputStreamReader);

        String currentLine = "";
        int counter = 0;

//        while (counter < 1000 && !currentLine.equals("stop")) {
//            currentLine = bufferRead.readLine();
//            //push input as subject
//            consoleInputLines.onNext(currentLine);
//            counter++;
//        }

//        consoleInputLines.onCompleted();

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                //3 slow messages over 15 secs
                for (int i = 0; i < 3; i++) {
                    consoleInputLines.onNext("next");
                    LOGGER.info("1 message pushed");
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //5 fast messages
                consoleInputLines.onNext("next");
                consoleInputLines.onNext("next");
                consoleInputLines.onNext("next");
                consoleInputLines.onNext("next");
                consoleInputLines.onNext("next");
                LOGGER.info("5 message pushed");

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                consoleInputLines.onCompleted();

                LOGGER.info("completed");

            }
        }, "the-thread");
        thread.start();
        thread.join();

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

        return perSecondSpeedometer.window(windowSize).flatMap(
                new Func1<Observable<Integer>, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Observable<Integer> window) {

                        return MathObservable.averageInteger(window);

                    }
                }
        ).observeOn(Schedulers.computation());
    }

    private Observable<Integer> speedometerOf(final PublishSubject<String> consoleInputLines,
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
}
