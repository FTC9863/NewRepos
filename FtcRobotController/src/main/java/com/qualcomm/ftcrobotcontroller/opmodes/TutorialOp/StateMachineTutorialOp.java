package com.qualcomm.ftcrobotcontroller.opmodes.TutorialOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class StateMachineTutorialOp extends LinearOpMode {

  public final int STATE_WAIT     = 0;
  public final int STATE_ACTION_1 = 1;
  public final int STATE_ACTION_2 = 2;
  public final int STATE_ACTINO_3 = 3;

  public int state      = STATE_ACTION_1;
  public int state_next = STATE_ACTION_1;

  @Override
  public void runOpMode() throws InterruptedException {

    waitForStart();

    while (opModeIsActive()) {

      switch (state){
        case STATE_WAIT:
          if(this.time>=0){
            state=state_next;
          }

      }


      waitOneFullHardwareCycle();
    }
  }
}