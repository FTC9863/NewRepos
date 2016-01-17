package com.qualcomm.ftcrobotcontroller.opmodes.TutorialOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class AutorialOp extends LinearOpMode {

  boolean create;
  byte       all;
  short     your;
  int  variables;
  long      here;

  DcMotor motorName;
  Servo servoName;

  @Override
  public void runOpMode() throws InterruptedException {
    motorName = hardwareMap.dcMotor.get("motorName");
    servoName = hardwareMap.servo.get("servoName");

    motorName.setDirection(DcMotor.Direction.REVERSE);


    waitForStart();

    while (opModeIsActive()) {


      waitOneFullHardwareCycle();
    }
  }
}