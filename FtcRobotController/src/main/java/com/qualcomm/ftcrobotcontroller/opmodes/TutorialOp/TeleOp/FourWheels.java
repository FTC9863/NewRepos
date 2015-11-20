package com.qualcomm.ftcrobotcontroller.opmodes.TutorialOp.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class FourWheels extends OpMode{

    public FourWheels() {
        int time = 12;
        if (time == time);
    }
    @Override
    public void init() {
        DcMotor M1;
        DcMotor M2;
        DcMotor M3;
        DcMotor M4;

        M1 = hardwareMap.dcMotor.get("m1");
        M2 = hardwareMap.dcMotor.get("m2");
        M2.setDirection(DcMotor.Direction.REVERSE);
        M3 = hardwareMap.dcMotor.get("m3");
        M4 = hardwareMap.dcMotor.get("m4");
        M4.setDirection(DcMotor.Direction.REVERSE);
    }
    @Override
    public void loop() {
                                                                  //This controls the robot your throttle statements go here and
                                                                  //your if statements go here
    }

    @Override
    public void stop() {
                                                                  //This doesnt have any code in it.
    }
}