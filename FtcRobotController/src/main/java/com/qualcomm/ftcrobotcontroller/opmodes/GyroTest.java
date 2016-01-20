/* Version 2.5.0
 * Update: Reformatting
 *
 * Supporting:
 * Color Sensor
 * Ir Sensor
 * Touch Sensor
 * Ultrasonic
 * Gyro
 * Compass
 * Light
 *
 * WARNING:
 *   This code is not to be used as a TeleOp code. It is to be used as a tutorial and some elements
 * of the code will over-ride the code and may cause errors within your code or your robot.
 */
package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.util.Range;

public class GyroTest extends OpMode {

    GyroSensor g;   //This is introducing a  Gyro Sensor
    double gva;     //gv for GyroValue A
    double gvb;

    public GyroTest() {}
    @Override
    public void init() {
        g = hardwareMap.gyroSensor.get("g");//G for Gyro
    }
    @Override
    public void loop() {
        gva = g.getRotation();


        telemetry.addData("Gyro Value", "Gyro Value:" + String.format("%.2f", gva));
    }
    @Override
    public void stop() {

    }
    double scaleInput(double dVal)  {
    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }
}