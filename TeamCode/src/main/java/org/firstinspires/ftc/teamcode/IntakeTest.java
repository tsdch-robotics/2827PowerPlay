package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="IntakeTest", group="Linear Opmode")
public class IntakeTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor rightWheel = null;
    private DcMotor leftWheel = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        rightWheel  = hardwareMap.get(DcMotor.class, "frontLeft");
        leftWheel = hardwareMap.get(DcMotor.class, "frontRight");

        double rightPower = 0;
        double leftPower = 0;

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                rightPower = 0.4;
                leftPower = 0.4;
            } else {
                rightPower = 0;
                leftPower = 0;
            }

            rightWheel.setPower(rightPower);
            leftWheel.setPower(leftPower);

        }
    }
}
