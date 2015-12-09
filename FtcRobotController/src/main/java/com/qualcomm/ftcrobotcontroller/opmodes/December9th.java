package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Oscar on 10/3/2015.
 */
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class December9th extends OpMode {

    /*
     * Note: the configuration of the servos is such that
     * as the arm servo approaches 0, the arm position moves up (away from the floor).
     * Also, as the claw servo approaches 0, the claw opens up (drops the game element).
     */
    // TETRIX VALUES.
    final static double FINGER_MIN_RANGE  = 0.10;
    final static double FINGER_MAX_RANGE  = 0.90;

    // position of the arm servo.
    double fingerLeftPosition;

    // amount to change the arm servo position.
    double fingerLeftDelta = 0.01;

    // position of the claw servo
    double fingerRightPosition;

    // amount to change the claw servo position by
    double fingerRightDelta = 0.01;

    boolean drivetype = true;

    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor motorjointRight;
    DcMotor motorjointLeft;
    DcMotor motorRight2;
    DcMotor motorLeft2;
    DcMotor motorArmJoint;

    Servo fingerLeft;
     Servo fingerRight;

    /**
     * Constructor
     */
    public December9th() {

    }

    /*
     * Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {

        /*This uses the hardware map to get the DC motors and servos.
         *It also states the names of the motors and servos for when we configure the robot.
         *It reverses some of the motors as well.
         */

        motorRight = hardwareMap.dcMotor.get("m1");
        motorLeft = hardwareMap.dcMotor.get("m2");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorjointRight = hardwareMap.dcMotor.get("mj1");
        motorjointLeft = hardwareMap.dcMotor.get("mj2");
        motorjointLeft.setDirection(DcMotor.Direction.REVERSE);
        motorRight2 = hardwareMap.dcMotor.get("m3");
        motorLeft2 = hardwareMap.dcMotor.get("m4");
        motorLeft2.setDirection(DcMotor.Direction.REVERSE);
        motorArmJoint = hardwareMap.dcMotor.get("m5");

        //fingerLeft = hardwareMap.servo.get("sfl");
        //fingerRight = hardwareMap.servo.get("sfr");

        // assign the starting position of the wrist and claw
        //fingerLeftPosition = 0.1;
        //fingerRightPosition = 0.1;
    }

    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
    @Override
    public void loop() {

		/*
		 * Gamepad 1
		 *
		 * Gamepad 1 controls the motors via the left stick, and it controls the
		 * wrist/claw via the a,b, x, y buttons
		 */

        // tank drive
        // note that if y equal -1 then joystick is pushed all of the way forward.
        float left = -gamepad1.left_stick_y;
        float right = -gamepad1.right_stick_y;
        float arm = -gamepad2.left_stick_y;
        boolean leftDPAD = gamepad1.dpad_left;
        boolean rightDPAD = gamepad1.dpad_right;

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);
        arm = Range.clip(arm, -1, 1);

        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        right = (float)scaleInput(right);
        left =  (float)scaleInput(left);

        arm = arm/3;

        motorArmJoint.setPower(arm);

        // update the position of the arm.

        if(leftDPAD == true){
            drivetype = true;
        }
        if(rightDPAD == true){
            drivetype = false;
        }
        if(drivetype == true){
            right = Range.clip(right, -1, 1);
            left = Range.clip(left, -1, 1);
            motorRight.setPower(-left);
            motorLeft.setPower(-right);
        }
        if(drivetype == false){
            right = Range.clip(right, -1, 1);
            left = Range.clip(left, -0.4f, 0.4f);
            motorRight.setPower(-right);
            motorLeft.setPower(-right);
            motorjointLeft.setPower(-left);
            motorjointRight.setPower(-left);
            motorRight2.setPower(right);
            motorLeft2.setPower(right);
        }
        if(gamepad2.a){

          //  fingerLeftPosition += fingerLeftDelta;
          //  fingerRightPosition -= fingerRightDelta;
        }
        if(gamepad2.b){

          //  fingerLeftPosition -= fingerLeftDelta;
          //  fingerRightPosition += fingerRightDelta;
        }


        // clip the position values so that they never exceed their allowed range.
        //fingerLeftPosition = Range.clip(fingerLeftPosition, FINGER_MIN_RANGE, FINGER_MAX_RANGE);
        //fingerRightPosition = Range.clip(fingerRightPosition, FINGER_MIN_RANGE, FINGER_MAX_RANGE);

        // write position values to the wrist and claw servo
        //fingerLeft.setPosition(fingerLeftPosition);
        //fingerRight.setPosition(fingerRightPosition);

		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */

        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("Left Drive:", motorLeft.getPower());
        telemetry.addData("Right Drive:", motorRight.getPower());
        telemetry.addData("Left Joint:", motorjointLeft.getPower());
        telemetry.addData("Right Joint:", motorjointRight.getPower());
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

