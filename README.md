# Arduino Plant Watering State Machine

## Introduction
 This is an event-driven state machine that waters a plant depending on the moisture level of the soil. 
 The event is triggered by a button on the Grove beginner kit for Arduino. Once an event is triggered, 
 it is interpreted by the Java source code I’ve made on IntelliJ, and an output of commands is enacted.

 The program automatically waters the plant for the user when an event is triggered after assessing if the soil is dry enough and then it plots data based on the soil moisture as a function of time. 
 Automatic watering systems like this are important because they eliminate the need for people to be actively involved which makes them useful. 
 It also collects data which is very important, especially if the user desires to keep track of how much water they might be utilising for different plants as well as testing to see if their plants are getting enough water.
 

## Technical Requirements/Specifications
This automated plant watering state machine has multiple functions.
This machine should be able to measure the moisture in the soil of the plant upon an IOEvent triggered by pressing the D6 button on the Arduino. 
After a measurement is taken, it assesses whether or not the soil is dry enough to be watered. If it is, multiple objects on the Arduino board activate. 
This includes the OLED displaying the message, “Watering Plant! \n Moist lvl:” following the current moisture level that was measured.
The pump turns on for approximately 10 seconds and the red LED also turns on indicating the system is active. 
A TimerTask class is used to take moisture measurements using the moisture sensor while the pump is on. After 10 seconds, the pump, red LED, and the TimerTask class deactivate. 
Finally, the OLed tells the user that the plant is watered with the new moisture level, and the data that was taken is graphed and displayed to the user. 

Two APIs were used in the making of this program, those being Firmata4j and StdLib. 
Firmata4j was used to help connect the Arduino board to the actual Java program and provided many methods that were implemented into the making of this machine.
This includes things like starting the board, creating pin objects for the sensor, pump, button, and red LED, as well as providing set/get value methods to control these pins. 
The StdLib API was used in the graphing of the data collected from the moisture sensor. 
Before graphing, the data from the moisture sensor was stored in an ArrayList using a TimerTask class and implementing the add method: graphingInfoList.add(voltageSensor.getValue());.
Through this, StdLib was used to create a graph by creating a canvas, X/Y scales, a pen to draw the data points, the X/Y axis, and then a for loop was implemented with the StdDraw.point() method to iterate through the ArrayList and plot all the points.


 ## Componenet List 
* Grove Kit Arduino Board
* Mosfet Switch	
* 9 Volt Battery
* Soil Moisture Sensor
* Water Pump  		
* Hardware to run the program on (Laptop in this case)
* Plant
* Water Transfer Pipe
* Container with water


## Final Thoughts
The plant watering state machine is a useful application that can make people's lives easier. 
Through the use of object-oriented programming and APIs with an Arduino board, this application also provides valuable information on moisture levels and possibly could be used for research on particular plants and how much water they require as well as how often. 
An automated watering system is only the base level and can be extended upon heavily.

 
