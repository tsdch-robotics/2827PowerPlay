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
public class Hannahteleop extends LinearOpMode {
    Hardware1 robot = new Hardware1();

    DcMotorEx frontLeft;
    DcMotorEx frontRight;
    DcMotorEx backLeft;
    DcMotorEx backRight;


    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    public void runOpMode() {
        double motorSpeed = 0.5;

        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        if (gamepad1.left_stick_y > 0) {
            frontLeft.setPower(motorSpeed);
            backLeft.setPower(motorSpeed);
        } else if (gamepad1.left_stick_y < 0) {
            frontLeft.setPower(-motorSpeed);
            backLeft.setPower(-motorSpeed);
        }

        if (gamepad1.right_stick_y > 0) {
            frontRight.setPower(motorSpeed);
            backRight.setPower(motorSpeed);
        } else if (gamepad1.right_stick_y < 0) {
            frontRight.setPower(-motorSpeed);
            backRight.setPower(-motorSpeed);
        }

    }

}

