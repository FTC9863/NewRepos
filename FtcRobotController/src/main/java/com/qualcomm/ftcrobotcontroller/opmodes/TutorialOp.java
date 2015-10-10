package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class TutorialOp extends OpMode {
    final static double ARM_MIN_RANGE  = 0.20;      //This are stating the servo minimums and maximums
    final static double ARM_MAX_RANGE  = 0.90;      //This are stating the servo minimums and maximums
    final static double CLAW_MIN_RANGE  = 0.20;     //This are stating the servo minimums and maximums
    final static double CLAW_MAX_RANGE  = 0.7;      //This are stating the servo minimums and maximums
    double armPosition;                             //This is a variable stating the variable of the arm position
    double armDelta = 0.1;                          //States The Arm delta and tells a position
    double clawPosition;                            //This is stating the Claw
    double clawDelta = 0.1;                         //This is stating the Claw delta
    DcMotor motorRight;                             //This is like robotc's #pragma
    DcMotor motorLeft;                              //This is like robotc's #pragma
    Servo claw;                                     //This is like robotc's #pragma
    Servo arm;                                      //This is like robotc's #pragma
    public TutorialOp() {
    }
    @Override
    public void init() {
        motorRight = hardwareMap.dcMotor.get("motor_2");        //The Code introduced the second motor
        motorLeft = hardwareMap.dcMotor.get("motor_1");         //The Code introduced the first motor
        motorLeft.setDirection(DcMotor.Direction.REVERSE);      //The Code stating that the direction of the motor is reverse
        arm = hardwareMap.servo.get("servo_1");                 //The Code introduced the First Servo
        claw = hardwareMap.servo.get("servo_6");                //The Code introduced the Second Servo
        armPosition = 0.2;                                      //This is stating the position of the arm.
        clawPosition = 0.2;                                     //This is stating the position of the servo.
    }
    @Override
    public void loop() {

        float throttle = -gamepad1.left_stick_y;                //This is stating that the game pad controls the robot
        float direction = gamepad1.right_stick_y;               //This is stating that the game pad controls the robot again
        float right = throttle - direction;                     //This is stating how the motors work
        float left = throttle + direction;                      //This is stating how the motors work as well

        right = Range.clip(right, -1, 1);                       //This is stating the robot controler can have a min of -1 and a max of 1
        left = Range.clip(left, -1, 1);                         //Same thing

        right = (float)scaleInput(right);                       //This is making the robot more persise at slower speeds
        left =  (float)scaleInput(left);                        //Same as above

        motorRight.setPower(right);                             //This makes the right motor go at the variable right
        motorLeft.setPower(left);                               //This makes the left motor go at the variable left

        // update the position of the arm.
        if (gamepad1.a) {
            // if the A button is pushed on gamepad1, increment the position of
            // the arm servo.
            armPosition += armDelta;
        }

        if (gamepad1.y) {
            // if the Y button is pushed on gamepad1, decrease the position of
            // the arm servo.
            armPosition -= armDelta;
        }

        // update the position of the claw
        if (gamepad1.x) {
            clawPosition += clawDelta;
        }

        if (gamepad1.b) {
            clawPosition -= clawDelta;
        }

        // clip the position values so that they never exceed their allowed range.
        armPosition = Range.clip(armPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);
        clawPosition = Range.clip(clawPosition, CLAW_MIN_RANGE, CLAW_MAX_RANGE);

        // write position values to the wrist and claw servo
        arm.setPosition(armPosition);
        claw.setPosition(clawPosition);



		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */
        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("arm", "arm:  " + String.format("%.2f", armPosition));
        telemetry.addData("claw", "claw:  " + String.format("%.2f", clawPosition));
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
