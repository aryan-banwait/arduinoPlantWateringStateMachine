import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class ArduinoFunctionsTest {
    /* This is a testing class for my EECS1021 Plant Watering Project. There will be multiple tests run in this
       class prior to starting the project. This includes tests like verifying if my pump and voltage sensor work
       as well as getting measurements on voltage values to use in my conditional statements to start the state
       machine. */

    IODevice arduinoBoard = new FirmataDevice(Pins.myPort);

    @Test
    public void ArduinoStartupTest() throws IOException, InterruptedException {
        arduinoBoard.start();
        arduinoBoard.ensureInitializationIsDone();
        assertTrue("Couldn't connect to Arduino Board",arduinoBoard.isReady());
        arduinoBoard.stop();
    }

    @Test
    public void VoltageSensorTest() throws IOException, InterruptedException {
        arduinoBoard.start();
        arduinoBoard.ensureInitializationIsDone();
        Pin voltageSensor = arduinoBoard.getPin(Pins.A1);
        voltageSensor.setMode(Pin.Mode.ANALOG);
        long value = voltageSensor.getValue();
        assertEquals(700, value,70);
        System.out.println("Voltage value: " + value);
        arduinoBoard.stop();
    }

    @Test
    public void PumpActivationTest() throws IOException, InterruptedException {
        arduinoBoard.start();
        arduinoBoard.ensureInitializationIsDone();
        Pin pump = arduinoBoard.getPin(Pins.D2);
        pump.setMode(Pin.Mode.OUTPUT);
        pump.setValue(1);
        assertEquals(1,pump.getValue());
        Thread.sleep(3000);
        pump.setValue(0);
        arduinoBoard.stop();
    }

    @Test
    public void FastVoltageMeasuringTest() throws IOException, InterruptedException {
        arduinoBoard.start();
        arduinoBoard.ensureInitializationIsDone();
        Pin voltageSensor = arduinoBoard.getPin(Pins.A1);
        voltageSensor.setMode(Pin.Mode.ANALOG);
        ArrayList<Long> moistureValues = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            moistureValues.add(voltageSensor.getValue());
            Thread.sleep(100);
        }
        System.out.println(moistureValues);
        arduinoBoard.stop();
    }
}
