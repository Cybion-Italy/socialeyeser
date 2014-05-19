package it.cybion.socialeyeser.trends;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

import java.util.Scanner;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class Demo {

    private static final Logger LOGGER = LoggerFactory.getLogger(Demo.class);

    public static void main(String[] args) {

        final Demo demo = new Demo();
        demo.run();

    }

    public Demo() {

    }

    private void run() {

        final PublishSubject<String> consoleInputLines = PublishSubject.create();

        //build observables
        final Observable<Integer> currentSpeed = it.cybion.socialeyeser.trends.Speed.speedometer(
                consoleInputLines, 1);

        final Observable<Integer> averageSpeed = it.cybion.socialeyeser.trends.Speed.movingAverageOf(
                5, currentSpeed);

        final Observable<Integer> filterGtEq = it.cybion.socialeyeser.trends.Filter.filterGtEq(3,
                currentSpeed);

        //subscribe loggers
        filterGtEq.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

                LOGGER.info("gteq 3 mps: " + integer);
            }
        });

        //subscribe loggers
        averageSpeed.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

                LOGGER.info("avg mps: " + integer);
            }
        });

        String inputLine = "";

        Scanner scanIn = new Scanner(System.in);

        while (!inputLine.equals("stop")) {
            inputLine = scanIn.nextLine();
            consoleInputLines.onNext(inputLine);
        }

        consoleInputLines.onCompleted();
        scanIn.close();

    }
}
