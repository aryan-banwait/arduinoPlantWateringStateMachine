import edu.princeton.cs.introcs.StdDraw;
import org.firmata4j.*;
import org.firmata4j.ssd1306.SSD1306;
import java.io.IOException;
import java.util.Timer;

public class PlantWaterMachine implements IODeviceEventListener {

    private final SSD1306 OLedDisplay;
    private final Pin voltageSensor;
    private final Pin pump;
    private final Pin redLED;
    private final Pin button;

    // Constructor that initializes pins and board from main class into Event driven class
    PlantWaterMachine(SSD1306 OLedDisplay,Pin voltageSensor,Pin pump,Pin redLED,Pin button) {
        this.OLedDisplay = OLedDisplay;
        this.voltageSensor = voltageSensor;
        this.pump = pump;
        this.redLED = redLED;
        this.button = button;
    }

    @Override
    public void onStart(IOEvent ioEvent) {}

    @Override
    public void onStop(IOEvent ioEvent) {}

    @Override
    public void onPinChange(IOEvent ioEvent) {

        // Starts program
        if(ioEvent.getPin().getIndex() == button.getIndex() && ioEvent.getValue() == 1) {

            // Defining conditional variables
            long currentSoilMoisture = voltageSensor.getValue();
            int drySoilValue = 650;
            int wetSoilValue = 550;

            if(currentSoilMoisture > drySoilValue) {

                // Displays info on OLed
                OLedDisplay.clear();
                OLedDisplay.getCanvas().drawString(0,0,"Watering Plant!");
                OLedDisplay.getCanvas().drawString(0,20,"Moist lvl: " + currentSoilMoisture);
                OLedDisplay.display();

                // Creating timer to get time and voltage values and store into an ArrayList
                GraphInfoTask gettingSamples = new GraphInfoTask(voltageSensor);
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(gettingSamples,100,100);

                // Starting pump and turning on red led
                try {
                    pump.setValue(1);
                    redLED.setValue(1);
                    Thread.sleep(10000);
                } catch (InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }

                // Turning pump and red led off
                try {
                    pump.setValue(0);
                    redLED.setValue(0);
                    Thread.sleep(1000);
                } catch (InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }

                // Turning off TimerTask
                timer.cancel();

                // Creating Graph to plot data
                StdDraw.clear();

                // X and Y scales
                StdDraw.setXscale(-20,150);
                StdDraw.setYscale(500,750);

                // Pen radius and color
                StdDraw.setPenRadius(0.005);
                StdDraw.setPenColor(StdDraw.BLUE);

                // Horizontal and Vertical Axis
                StdDraw.line(0,500,105,500); // X-axis
                StdDraw.line(0,500,0,750);   // Y-axis

                // Labels
                StdDraw.text(52.5,493,"Samples");
                StdDraw.text(-15,625,"Moisture");
                StdDraw.text(52.5,745,"Soil Moisture Changes");

                // Drawing data points
                for(int i = 0; i < GraphInfoTask.moistureValues.size(); i++) {
                    StdDraw.point(i + 1,GraphInfoTask.moistureValues.get(i));
                }

                // Displaying Graph Info to Console
                System.out.println(GraphInfoTask.moistureValues);

                long biggestMoistureValue = GraphInfoTask.moistureValues.getFirst();
                for(int i = 1; i < GraphInfoTask.moistureValues.size(); i++) {
                    if(GraphInfoTask.moistureValues.get(i) >= biggestMoistureValue) {
                        biggestMoistureValue = GraphInfoTask.moistureValues.get(i);
                    }
                }

                long smallestMoistureValue = GraphInfoTask.moistureValues.getFirst();
                for(int i = 1; i < GraphInfoTask.moistureValues.size(); i++) {
                    if(GraphInfoTask.moistureValues.get(i) <= smallestMoistureValue) {
                        smallestMoistureValue = GraphInfoTask.moistureValues.get(i);
                    }
                }

                System.out.print("Highest: " + biggestMoistureValue + " ");
                System.out.print("Lowest: " + smallestMoistureValue + " ");
                System.out.println("STD: " + (biggestMoistureValue - smallestMoistureValue));

                // Displaying new moisture info to OLed
                OLedDisplay.clear();
                OLedDisplay.getCanvas().drawString(0,0,"Plant Watered!");
                OLedDisplay.getCanvas().drawString(0,20,"new Moist lvl: " + voltageSensor.getValue());
                OLedDisplay.display();

                try {
                    Thread.sleep(3000);
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                OLedDisplay.clear();
            }

            else if(currentSoilMoisture < wetSoilValue) {

                // Displays info on OLed and makes sures pins are deactivated
                OLedDisplay.clear();
                OLedDisplay.getCanvas().drawString(0,0,"Already wet!");
                OLedDisplay.getCanvas().drawString(0,20,"Moist lvl: " + currentSoilMoisture);
                OLedDisplay.display();

                try {
                    Thread.sleep(3000);
                    pump.setValue(0);
                    redLED.setValue(0);
                }
                catch (InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }

                OLedDisplay.clear();
            }
        }
    }

    @Override
    public void onMessageReceive(IOEvent ioEvent, String s) {}
}