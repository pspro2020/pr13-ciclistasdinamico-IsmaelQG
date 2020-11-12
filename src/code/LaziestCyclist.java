package code;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class LaziestCyclist implements Runnable{
	
	private final String name;
    private final Phaser phaser;
	private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	public LaziestCyclist(String name, Phaser phaser) {
		this.name = name;
		this.phaser = phaser;
	}

	@Override
	public void run() {
		if(!phaser.isTerminated()) {
			int joinPhase = phaser.register();
			try {
				quedarGasolinera();
			}
			catch(InterruptedException e) {
				return;
			}
			if (joinPhase <= CyclistsPhaser.LLEGAR_GASOLINERA_PHASE) {
                try {
                    phaser.awaitAdvanceInterruptibly(phaser.arrive());
                } catch (InterruptedException e) {
                    return;
                }
            }
			try {
				llegarVenta();
			}
			catch(InterruptedException e) {
				return;
			}
			if (joinPhase <= CyclistsPhaser.LLEGAR_VENTA_PHASE) {
                try {
                    phaser.awaitAdvanceInterruptibly(phaser.arrive());
                } catch (InterruptedException e) {
                    return;
                }
            }
			try {
				vueltaGasolinera();
			}
			catch(InterruptedException e) {
				return;
			}
			if (joinPhase <= CyclistsPhaser.VOLVER_GASOLINERA_PHASE) {
                try {
                    phaser.awaitAdvanceInterruptibly(phaser.arrive());
                } catch (InterruptedException e) {
                    return;
                }
            }
			try {
				vueltaCasa();
			}
			catch(InterruptedException e) {
				return;
			}
		}
		else {
            System.out.printf("%s -> %s Llego tarde\n",
                    LocalTime.now().format(dateTimeFormatter), name);
        }
	}
	
	private void quedarGasolinera() throws InterruptedException{
		System.out.printf("%s -> %s sale de casa\n",
                LocalTime.now().format(dateTimeFormatter), name);
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(1, 3 + 1));
        System.out.printf("%s -> %s ha llegado a la gasolinera\n",
                LocalTime.now().format(dateTimeFormatter), name);
	}
	
	private void llegarVenta() throws InterruptedException{
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5, 10 + 1));
        System.out.printf("%s -> %s ha llegado a la venta\n",
                LocalTime.now().format(dateTimeFormatter), name);
	}
	
	private void vueltaGasolinera() throws InterruptedException{
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5, 10 + 1));
        System.out.printf("%s -> %s ha llegado a la gasolinera de vuelta\n",
                LocalTime.now().format(dateTimeFormatter), name);
	}
	
	private void vueltaCasa() throws InterruptedException{
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(1, 3 + 1));
        System.out.printf("%s -> %s ha llegado a su casa\n",
                LocalTime.now().format(dateTimeFormatter), name);
	}

}
