import org.firmata4j.Pin;
import java.util.ArrayList;
import java.util.TimerTask;

public class GraphInfoTask extends TimerTask {

    private final Pin voltageSensor;
    public static ArrayList<Long> moistureValues;

    GraphInfoTask(Pin voltageSensor) {
        this.voltageSensor = voltageSensor;
        moistureValues = new ArrayList<>();
    }

    @Override
    public void run() {
        moistureValues.add(voltageSensor.getValue());
    }
}
