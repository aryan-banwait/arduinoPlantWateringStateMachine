import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.ssd1306.SSD1306;
import org.firmata4j.I2CDevice;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        // Initializing Arduino Board
        IODevice myBoard = new FirmataDevice(Pins.myPort);
        myBoard.start();
        myBoard.ensureInitializationIsDone();

        // Initializing OLed Display
        I2CDevice OLedWires = myBoard.getI2CDevice(Pins.I2C0);
        SSD1306 OLedDisplay = new SSD1306(OLedWires, SSD1306.Size.SSD1306_128_64);
        OLedDisplay.init();

        //Setting up Sensor
        Pin voltageSensor = myBoard.getPin(Pins.A1);
        voltageSensor.setMode(Pin.Mode.ANALOG);

        // Initializing Pump
        Pin pump = myBoard.getPin(Pins.D2);
        pump.setMode(Pin.Mode.OUTPUT);

        // Initializing Red LED
        Pin redLED = myBoard.getPin(Pins.D4);
        redLED.setMode(Pin.Mode.OUTPUT);

        // Initializing Button
        Pin button = myBoard.getPin(Pins.D6);
        button.setMode(Pin.Mode.INPUT);

        // Adding IO event driven class to myBoard
        PlantWaterMachine plantWaterMachine = new PlantWaterMachine(OLedDisplay, voltageSensor, pump, redLED, button);
        myBoard.addEventListener(plantWaterMachine);

    }
}
