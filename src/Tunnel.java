import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Tunnel extends Stage {
    private static final ArrayBlockingQueue<Car> lineToTonnel = new ArrayBlockingQueue((Integer) Main.CARS_COUNT / 2);
    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }
    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                lineToTonnel.put(c);
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                Main.countStagesIsFinished++;
                c.countStagesIsFinished++;
                if (Main.winner == null && c.countStagesIsFinished == Car.countStagesToFinished) {
                    Main.winner = c;
                }
                lineToTonnel.take();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}