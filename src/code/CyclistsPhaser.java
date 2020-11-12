package code;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Phaser;

public class CyclistsPhaser extends Phaser{
	
	public static final int LLEGAR_GASOLINERA_PHASE = 0;
    public static final int LLEGAR_VENTA_PHASE = 1;
    public static final int VOLVER_GASOLINERA_PHASE = 2;

	private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        switch (phase) {
            case LLEGAR_GASOLINERA_PHASE:
                System.out.printf("%s -> Todos los %d ciclistas llegaron a la gasolinera (executed in %s)\n",
                        LocalTime.now().format(dateTimeFormatter), registeredParties,
                        Thread.currentThread().getName());
                break;
            case LLEGAR_VENTA_PHASE:
                System.out.printf("%s -> Todos los %d ciclistas llegaron a la venta (executed in %s)\n",
                        LocalTime.now().format(dateTimeFormatter), registeredParties,
                        Thread.currentThread().getName());
                break;
            case VOLVER_GASOLINERA_PHASE:
                System.out.printf("%s -> Todos los %d ciclistas llegaron a la gasolinera y vuelven a casa (executed in %s)\n",
                        LocalTime.now().format(dateTimeFormatter), registeredParties,
                        Thread.currentThread().getName());
                return true;
        }
        return super.onAdvance(phase, registeredParties);
    }

}
