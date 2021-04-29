import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static java.lang.Thread.sleep;

public class Main {
    public static final int CARS_COUNT = 4;
    public static final CyclicBarrier readyToStart = new CyclicBarrier(CARS_COUNT + 1);
    public static final CyclicBarrier start = new CyclicBarrier(CARS_COUNT + 1);
    public static int countStagesIsFinished = 0;
    public static Car winner = null;

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));

        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
            Car.countStagesToFinished = race.getStages().size();

        }

        int countStagesToBeFinished = race.getStages().size() * cars.length;

        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        try {
            readyToStart.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        try {
            start.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            while (true) {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Main.countStagesIsFinished == countStagesToBeFinished) {
                    System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
                    System.out.println("Победитель - " + winner.getName());
                    break;
                }
            }

        }).start();

    }
}
