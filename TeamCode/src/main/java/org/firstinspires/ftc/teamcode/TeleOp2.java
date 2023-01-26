package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp1", group="Linear Opmode")
public class TeleOp2 extends LinearOpMode {

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

    static final int highGoal = 20;
    static final int medGoal = 15;
    static final int lowGoal = 10;
    private int most = 0;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized yuh");
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
        //rightHand = hardwareMap.get(Servo.class, "right_hand");

        double minposL = 0.4, maxposL = 0.73, minposR = 0.1, maxposR = 0.43;

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        armVert.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armVert.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        double gripposL = 0.7, gripposR = 0.4;
        double adjustedPos = maxposR;

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

            double drive = gamepad1.left_stick_y;
            double turn  =  -gamepad1.right_stick_x;

            leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;
/*
            leftPower  = gamepad1.left_stick_y ;
            rightPower = gamepad1.right_stick_y ;

            //set armvertpower
            if (gamepad1.left_trigger > 0) {
                armVertPower = 0.9;
                //armHorPower = 0.4;
            }
            else if (gamepad1.right_trigger > 0) {
                armVertPower = -0.45;
                //armHorPower = -0.25;
            }
            else {
                armVertPower = 0.08;
                //armHorPower = 0.15;
            }
*/
            //0.23

            if (gamepad1.a){
                armVert.setTargetPosition(0); //level at 0, grabbing
                armVert.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armVert.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                armVert.setPower(0.4);
                most = armVert.getCurrentPosition();
            }
            if (gamepad1.b){
                armVert.setTargetPosition(200); //level at 0, grabbing
                armVert.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armVert.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                armVert.setPower(0.4);
                most = armVert.getCurrentPosition();
            }
            if (gamepad1.y){
                armVert.setTargetPosition(500); //level at 0, grabbing
                armVert.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armVert.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                armVert.setPower(0.4);
                most = armVert.getCurrentPosition();
            }


            //open the gripper on X button if not already at most open position.
            if (gamepad1.left_bumper && gripposR < maxposR) gripposR = gripposR + .01;
            //if (gamepad1.x && gripposL < maxposL) gripposL = gripposL + .01;

            // close the gripper on Y button if not already at the closed position.
            //if (gamepad1.y && gripposL > minposL) gripposL = gripposL - .01;
            if (gamepad1.right_bumper && gripposR > minposR) gripposR = gripposR - .01;

/*
            if (gamepad1.dpad_left && adjustedPos = maxPos) {
                gripposR = gripposR + 0.05;
                maxposR = maxposR + 0.05;
            }
            else {
                gripposR = gripposR;
                maxposR = maxposR;
            }
            if (gamepad1.dpad_right) {
                gripposR = gripposR - 0.05;
                minposR = minposR - 0.05;
            }
            else {
                gripposR = gripposR;
                minposR = minposR;
            }

*/
            // Send power to wheels, arms, and servos
            frontLeft.setPower(0.7*leftPower);
            frontRight.setPower(0.7*rightPower);
            backLeft.setPower(0.7*leftPower);
            backRight.setPower(0.7*rightPower);
            //armVert.setPower(armVertPower);
            //armHor.setPower(armHorPower);

            leftHand.setPosition(gripposR);
            //rightHand.setPosition(gripposR);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            //telemetry.addData("Motors", "left (%.2f), right (%.2f)", left, right);
            //telemetry.addData("Uncontrollable farting", "I cant stop the farting oh my god");

            telemetry.update();
        }
    }
}
