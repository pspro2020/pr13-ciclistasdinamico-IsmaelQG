package code;

import java.util.concurrent.TimeUnit;

public class Main {

	private static final int CYCLISTS = 10;

    public static void main(String[] args) throws InterruptedException {
        CyclistsPhaser phaser = new CyclistsPhaser();
        for (int i = 0; i < CYCLISTS; i++) {
            new Thread(new Cyclist("Cyclist " + i, phaser), "Cyclist " + i).start();
        }
        new Thread(new ImpacientCyclist("Impacient cyclist", phaser), "Impacient cyclist").start();
        new Thread(new LazyCyclist("Lazy cyclist", phaser), "Lazy cyclist").start();
        TimeUnit.SECONDS.sleep(60);
        new Thread(new LaziestCyclist("Laziest cyclist", phaser), "Laziest cyclist").start();
    }

}
