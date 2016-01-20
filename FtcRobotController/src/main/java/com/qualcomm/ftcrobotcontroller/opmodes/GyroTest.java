package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;

public class GyroTest extends OpMode {

    GyroSensor gyro;   //This is introducing a  Gyro Sensor
    double gva;     //gv for GyroValue A
    double gvb;


    public GyroTest() {}
    @Override public void init(){
        gyro = hardwareMap.gyroSensor.get("gyro");//G for Gyro
        }
    @Override public void loop(){
        gva = gyro.getRotation();
        telemetry.addData("Gyro Value", "Gyro Value:" + String.format("%.2f", gva));}
    @Override public void stop(){}}