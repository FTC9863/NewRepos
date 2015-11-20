package com.qualcomm.ftcrobotcontroller.opmodes.MRDV;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class MRDV2 extends OpMode {
    Boolean drivetype;

    DcMotor LeftDrive1;    //1/8
    DcMotor RightDrive1;   //2/8
    DcMotor LeftDrive2;    //3/8
    DcMotor RightDrive2;   //4/8
    DcMotor MotorJointL;   //5/8
    DcMotor MotorJointR;   //6/8
    DcMotor ArmMotor;      //7/8
    //DcMotor motorName;     //8/8

    Servo sal;               //01/12             //sal is "Servo Arm Left"
    Servo sar;               //02/12             //sar is "Servo Arm Right"
    //Servo servoName;       //03/12
    //Servo servoName;       //04/12
    //Servo servoName;       //05/12
    //Servo servoName;       //06/12
    //Servo servoName;       //07/12
    //Servo servoName;       //08/12
    //Servo servoName;       //09/12
    //Servo servoName;       //10/12
    //Servo servoName;       //11/12
    //Servo servoName;       //12/12

    public MRDV2() {
    }
    @Override
    public void init() {
        LeftDrive1 = hardwareMap.dcMotor.get("M1");
        RightDrive1 = hardwareMap.dcMotor.get("M2");
        RightDrive1.setDirection(DcMotor.Direction.REVERSE);
        LeftDrive2 = hardwareMap.dcMotor.get("M3");
        RightDrive2 = hardwareMap.dcMotor.get("M4");
        RightDrive2.setDirection(DcMotor.Direction.REVERSE);
        MotorJointL = hardwareMap.dcMotor.get("MJ1");
        MotorJointR = hardwareMap.dcMotor.get("MJ2");
        MotorJointR.setDirection(DcMotor.Direction.REVERSE);
        ArmMotor = hardwareMap.dcMotor.get("AM");
        //ArmMotor.setDirection(DcMotor.Direction.REVERSE);

        sal = hardwareMap.servo.get("sal");
        sar = hardwareMap.servo.get("sar");
    }
    @Override
    public void loop() {

        boolean top = gamepad1.dpad_up;
        boolean bottom = gamepad1.dpad_down;
        boolean leftDPAD = gamepad1.dpad_left;
        boolean rightDPAD = gamepad1.dpad_right;

        float throttle = -gamepad1.left_stick_y;
        float direction = gamepad1.right_stick_y;
        float right = throttle - direction;
        float left = throttle + direction;
        float mArm = gamepad2.right_stick_y;  //m-Arm is Motor Arm
        float sArm = gamepad2.left_stick_y;   //s-Arm is Servo Arm
        float sArm2 = 0;                      //sArm2 is Servo Arm***Part 2***

        if(sArm < 0.1){      //There are 3 servo parts. One is a Threshold, The next is the one that
            sArm2 = 0;       //gets modifed for sArm3, and sArm3 is what actually controls the servo
        }
        else if(sArm >= 0.1){
            sArm2 = sArm;
        }
        double sArm3 =(sArm2/2)+0.5;           //sArm3 is Servo Arm***Part 3***

        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        right = (float) scaleInput(right);
        left = (float) scaleInput(left);

        RightDrive1.setPower(right);
        LeftDrive1.setPower(left);

        ArmMotor.setPower(mArm);
        sal.setPosition(sArm3);
        sar.setPosition(-sArm3);

        if(leftDPAD){
            drivetype = true;
        }
        if(rightDPAD){
            drivetype = false;
        }
        if(drivetype == true){
            right = Range.clip(right, -1, 1);
            left = Range.clip(left, -1, 1);
            RightDrive1.setPower(right);
            LeftDrive1.setPower(left);
        }
        if(drivetype == false){
            right = Range.clip(right, -1, 1);
            left = Range.clip(left, -0.4f, 0.4f);
            RightDrive1.setPower(-right);
            LeftDrive1.setPower(-right);
            MotorJointL.setPower(left);
            MotorJointR.setPower(left);
            RightDrive2.setPower(-right);
            LeftDrive2.setPower(-right);
        }

        telemetry.addData("Text", "*** Robot Data***");
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