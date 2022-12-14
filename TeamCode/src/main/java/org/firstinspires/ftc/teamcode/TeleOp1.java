package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp1", group="Linear Opmode")
public class TeleOp1 extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor armVert = null;
    //private DcMotor armHor = null;

    private Servo leftHand = null;
    private Servo rightHand = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
         // step (using the FTC Robot Controller app on the phone).
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        armVert = hardwareMap.get(DcMotor.class, "armVert");
        //armHor = hardwareMap.get(DcMotor.class, "armHor");

        leftHand = hardwareMap.get(Servo.class, "left_hand");
        rightHand = hardwareMap.get(Servo.class, "right_hand");

        double minposL = 0, maxposL = 0, minposR = -0.3, maxposR = 0.45;

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        double gripposL = 0.5, gripposR = -0.25;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double leftPower;
            double rightPower;
            double armVertPower;
            double armHorPower;

            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
/*
            double left = gamepad1.left_stick_y;
            double right = gamepad1.right_stick_y;
*/
            double drive = gamepad1.left_stick_y;
            double turn  =  -gamepad1.right_stick_x;

            leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

            //set armvertpower
            if (gamepad1.a) {
                armVertPower = 0.7;
                //armHorPower = 0.4;
            }
            else if (gamepad1.b) {
                armVertPower = 0;
                //armHorPower = -0.25;
            }
            else {
                armVertPower = 0.23;
                //armHorPower = 0.15;
            }


            //open the gripper on X button if not already at most open position.
            if (gamepad1.x && gripposR < maxposR) gripposR = gripposR + .01;
            //if (gamepad1.x && gripposL < maxposL) gripposL = gripposL + .01;

            // close the gripper on Y button if not already at the closed position.
            //if (gamepad1.y && gripposL > minposL) gripposL = gripposL - .01;
            if (gamepad1.y && gripposR > minposR) gripposR = gripposR - .01;


            // Send power to wheels, arms, and servos
            frontLeft.setPower(leftPower);
            frontRight.setPower(rightPower);
            backLeft.setPower(leftPower);
            backRight.setPower(rightPower);
            armVert.setPower(armVertPower);
            //armHor.setPower(armHorPower);

            leftHand.setPosition(gripposL);
            rightHand.setPosition(gripposR);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            //telemetry.addData("Motors", "left (%.2f), right (%.2f)", left, right);
            telemetry.addData("Uncontrollable farting", "I cant stop the farting oh my god");

            telemetry.update();
        }
    }
}
