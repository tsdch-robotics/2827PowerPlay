package org.firstinspires.ftc.teamcode;

import androidx.annotation.Nullable;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
//idk what any of this is or if i need or not but i copied and pasted from OutreachBot;


import org.firstinspires.ftc.robotcontroller.external.samples.BasicOmniOpMode_Linear;


@TeleOp()
public class Hannahteleop extends{
    Hardware1 robot = new Hardware1();

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    public void init() {
        double motorSpeed = 0.5;
        robot.init(Hardware1);



    }
}



