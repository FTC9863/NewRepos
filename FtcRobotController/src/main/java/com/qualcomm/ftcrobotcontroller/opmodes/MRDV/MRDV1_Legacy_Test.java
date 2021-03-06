package com.qualcomm.ftcrobotcontroller.opmodes.MRDV;

/**
 * Created by Oscar on 10/5/2015.
 */
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class MRDV1_Legacy_Test extends OpMode {

    /*
     * Note: the configuration of the servos is such that
     * as the arm servo approaches 0, the arm position moves up (away from the floor).
     * Also, as the claw servo approaches 0, the claw opens up (drops the game element).
     */
    // TETRIX VALUES.
    final static double ARM_MIN_RANGE  = 0.20;
    final static double ARM_MAX_RANGE  = 0.90;
    final static double CLAW_MIN_RANGE  = 0.20;
    final static double CLAW_MAX_RANGE  = 0.7;

    // position of the arm servo.
    double armPosition;

    // amount to change the arm servo position.
    double armDelta = 0.1;

    // position of the claw servo
    double clawPosition;

    // amount to change the claw servo position by
    double clawDelta = 0.1;

    boolean drivetype = true;

    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor motorarm;
    DcMotor motorjointLeft;
    DcMotorController JointController;
    DcMotor motorRight2;
    DcMotor motorLeft2;

    //Servo claw;
    // Servo arm;

    /**
     * Constructor
     */
    public MRDV1_Legacy_Test() {

    }

    /*
     * Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {
		/*
		 * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */

		/*
		 * For the demo Tetrix K9 bot we assume the following,
		 *   There are two motors "motor_1" and "motor_2"
		 *   "motor_1" is on the right side of the bot.
		 *   "motor_2" is on the left side of the bot.
		 *
		 * We also assume that there are two servos "servo_1" and "servo_6"
		 *    "servo_1" controls the arm joint of the manipulator.
		 *    "servo_6" controls the claw joint of the manipulator.
		 */
        motorRight = hardwareMap.dcMotor.get("m1");
        motorLeft = hardwareMap.dcMotor.get("m2");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorarm = hardwareMap.dcMotor.get("ma1");
        motorjointLeft = hardwareMap.dcMotor.get("mj2");
        motorjointLeft.setDirection(DcMotor.Direction.REVERSE);
        JointController = hardwareMap.dcMotorController.get("jc1");
        motorRight2 = hardwareMap.dcMotor.get("m3");
        motorLeft2 = hardwareMap.dcMotor.get("m4");
        motorLeft2.setDirection(DcMotor.Direction.REVERSE);
        motorLeft2.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motorRight2.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        // arm = hardwareMap.servo.get("servo_1");
        // claw = hardwareMap.servo.get("servo_6");

        // assign the starting position of the wrist and claw
        armPosition = 0.2;
        clawPosition = 0.2;
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
        boolean top = gamepad1.dpad_up;
        boolean bottom = gamepad1.dpad_down;
        boolean leftDPAD = gamepad1.dpad_left;
        boolean rightDPAD = gamepad1.dpad_right;

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        right = (float)scaleInput(right);
        left =  (float)scaleInput(left);

        // write the values to the motors
        if (top == true){


            motorarm.setPower(0.4f);
        }  if (bottom == true){


            motorarm.setPower(-0.4f);
        }
        if (bottom == false && top == false){


            motorarm.setPower(0.0f);
        }

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
            motorjointLeft.setPower(left);
            motorjointLeft.setPower(left);
            motorRight2.setPower(-right);
            motorLeft2.setPower(-right);
        }


        // update the position of the arm.


        if (gamepad1.a) {



        }

        if (gamepad1.y) {
            // if the Y button is pushed on gamepad1, decrease the position of
            // the arm servo.
            armPosition -= armDelta;
        }

        // update the position of the claw
        if (gamepad1.left_bumper) {
            clawPosition += clawDelta;
        }

        if (gamepad1.left_trigger > 0.25) {
            clawPosition -= clawDelta;
        }

        if (gamepad1.b) {
            clawPosition -= clawDelta;
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
        // arm.setPosition(armPosition);
        // claw.setPosition(clawPosition);

		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */

        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("Left Drive:", motorLeft.getPower());
        telemetry.addData("Right Drive:", motorRight.getPower());
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

