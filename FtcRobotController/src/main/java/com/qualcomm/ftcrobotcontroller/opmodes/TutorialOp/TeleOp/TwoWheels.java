package com.qualcomm.ftcrobotcontroller.opmodes.TutorialOp.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

public class TwoWheels extends OpMode{

    public TwoWheels() {
    }
    @Override
    public void init() {
        DcMotor M1;
        DcMotor M2;

        M1 = hardwareMap.dcMotor.get("m1");
        M2 = hardwareMap.dcMotor.get("m2");
        M2.setDirection(DcMotor.Direction.REVERSE);
    }
    @Override
    public void loop() {
        float throttle = -gamepad1.left_stick_y;
        float direction = gamepad1.right_stick_y;
        float right = throttle - direction;
        float left = throttle + direction;

        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        //M1.setpower(right);
        //M2.setpower(left);
    }

    @Override
    public void stop() {
                                                                  //This doesnt have any code in it.
    }
}