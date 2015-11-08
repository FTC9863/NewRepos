/* Version 2.4.9
 * Update: Reformatting
 * Update: Sensor Support
 *
 * Supporting:
 * Color Sensor
 * Ir Sensor
 * Touch Sensor
 * Ultrasonic
 * Gyro
 * *Compass*
 * *Light*
 *
 * WARNING:
 *   This code is not to be used as a TeleOp code. It is to be used as a tutorial and some elements
 * of the code will over-ride the code and may cause errors within your code or your robot.
 */
package com.qualcomm.ftcrobotcontroller.opmodes.TutorialOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
//Sensors
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.LightSensor;

public class TutorialTwoOp extends OpMode{

    final static double servoName_MIN_RANGE  = 0.20;              //This is a variable created to be used later
    final static double servoName_MAX_RANGE  = 0.90;              //This is a variable created to be used later
    double servoPosition;                                         //This is a variable representing the servo's position.
    double servoDelta = 0.1;                                      //This is stating the location of one the servo variables

    DcMotor motorName;                                            //This is introducing a  DcMotor
    Servo servoName;                                              //This is introducing a  Servo
    ColorSensor cSensorName;                                      //This is introducing a  Color Sensor
    IrSeekerSensor irsSensorName;                                 //This is introducing an IrSeeker Sensor
    TouchSensor tSensorName;                                      //This is introducing a  Touch sensors
    UltrasonicSensor usSensorName;                                //This is introducing an Ultrasonic Sensor
    GyroSensor gSensorName;                                       //This is introducing a  Gyro Sensor
    CompassSensor cpsSensorName;                                  //This is introducing a  Compass Sensor
    LightSensor lsensorName;                                      //This is introducing a  Light Sensor

    public TutorialTwoOp() {
    }
    @Override
    public void init() {
        motorName = hardwareMap.dcMotor.get("motorName");         //This is what the phone calls the motor
        motorName.setDirection(DcMotor.Direction.REVERSE);        //This is saying in the code that the motor is in reverse mode
        servoName = hardwareMap.servo.get("servoName");           //This is what the phone calls the motor
        servoName.setDirection(Servo.Direction.REVERSE);          //This is saying in the code that the servo is in reverse mode
        servoPosition = 0.2;                                      //This is stating the servoPosition variable's value

    }
    @Override
    public void loop() {

        float throttle = -gamepad1.left_stick_y;                  //This is stating the variable throttle, and stating its value
        float direction = gamepad1.right_stick_y;                 //This is stating the variable direction, and stating its value
        float right = throttle - direction;                       //This is stating the variable right
        float left = throttle + direction;                        //This is stating the variable left

        right = Range.clip(right, -1, 1);                         //This is stating that the variables right and left have a
        left = Range.clip(left, -1, 1);                           //minimum of -1, and a maximum of 1.

        right = (float) scaleInput(right);                         //This is making the variables right and left more accurate
        left = (float) scaleInput(left);                          //at lower values

        motorName.setPower(right);                                //This is making the variable motorName the power of the
        motorName.setPower(left);                                 //variables right and left.

        if (gamepad1.a) {                                         //This is an If statement for gamepad button a.

        }
        if (gamepad1.y) {                                         //This is an If statement for gamepad button y.
        }

        if (cSensorName.equals(0)) {
        }

        if (irsSensorName.signalDetected()){
        }

        if (tSensorName.isPressed()){
        }

        if (usSensorName.equals(0)){
        }

        if (gSensorName.equals(0)){
        }

        if (cpsSensorName.equals(0)){
        }

        if (lsensorName.equals(0)){
        }

        //  WARNING:(2)
        //  CODE BELOW NOT YET MODIFIED
        //  CODE BELOW NOT YET COMPLETELY UNDERSTOOD

        servoPosition = Range.clip(servoPosition, servoName_MIN_RANGE, servoName_MAX_RANGE);
        servoName.setPosition(servoPosition);

        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("arm", "arm:  " + String.format("%.2f", servoPosition));
        telemetry.addData("left tgt pwr", "left  pwr: " + String.format("%.2f", left));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
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