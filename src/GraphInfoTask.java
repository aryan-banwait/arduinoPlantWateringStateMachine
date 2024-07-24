import org.firmata4j.Pin;
import java.util.ArrayList;
import java.util.TimerTask;

public class GraphInfoTask extends TimerTask {

    private final Pin voltageSensor;
    public final ArrayList<Long> moistureValues;

    GraphInfoTask(Pin voltageSensor, ArrayList<Long> moistureValues) {
        this.voltageSensor = voltageSensor;
        this.moistureValues = moistureValues;
    }

    @Override
    public void run() {
        moistureValues.add(voltageSensor.getValue());
    }
}
