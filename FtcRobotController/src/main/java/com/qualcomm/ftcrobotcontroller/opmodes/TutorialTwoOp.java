package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/* Version 2.1.3
 * Update: Reformating
 */

/*
 * WARNING:
 *   This code is not to be used as a TeleOp code. It is to be used as a tutorial and some elements
 * of the code will over-ride the code and may cause errors within your code or your robot.
 */

public class TutorialTwoOp extends OpMode {
    final static double servoName_MIN_RANGE  = 0.20;              //This are stating the servo minimums and maximums
    final static double servoName_MAX_RANGE  = 0.90;              //This are stating the servo minimums and maximums
    double servoPosition;                                         //This is a variable stating the variable of the arm position
    double servoDelta = 0.1;                                      //States The Arm delta and tells a position

    DcMotor motorName;                                            //This is like robotc's #pragma
    Servo servoName;                                              //This is like robotc's #pragma

    public TutorialTwoOp() {
    }
    @Override
    public void init() {
        motorName = hardwareMap.dcMotor.get("motorName");         //The Code introduced the second motor
        motorName.setDirection(DcMotor.Direction.REVERSE);        //The Code stating that the direction of the motor is reverse
        servoName = hardwareMap.servo.get("servoName");           //The Code introduced the First Servo
        servoPosition = 0.2;                                      //This is stating the position of the arm.

    }
    @Override
    public void loop() {

        float throttle = -gamepad1.left_stick_y;                  //This is stating that the game pad controls the robot
        float direction = gamepad1.right_stick_y;                 //This is stating that the game pad controls the robot again
        float right = throttle - direction;                       //This is stating how the motors work
        float left = throttle + direction;                        //This is stating how the motors work as well

        right = Range.clip(right, -1, 1);                         //This is stating the robot controler can have a min of -1 and a max of 1
        left = Range.clip(left, -1, 1);                           //Same thing

        right = (float)scaleInput(right);                         //This is making the robot more persise at slower speeds
        left =  (float)scaleInput(left);                          //Same as above

        motorName.setPower(right);                                //This makes the right motor go at the variable right
        motorName.setPower(left);                                 //This makes the left motor go at the variable left

        if (gamepad1.a) {                                         //This is an If statement. Controlding the Arm
            servoPosition += servoDelta;
        }

        if (gamepad1.y) {
            servoPosition -= servoDelta;
        }

        //  WARNING:(2)
        //  CODE BELOW NOT YET MODIFIED
        //  CODE BELOW NOT YET COMPLETELY UNDERSTOOD

        servoPosition = Range.clip(servoPosition, servoName_MIN_RANGE, servoName_MAX_RANGE);
        servoName.setPosition(servoPosition);

        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("arm", "arm:  " + String.format("%.2f", servoPosition));
        telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", left));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));

    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {

    }


    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal)  {
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
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

}
