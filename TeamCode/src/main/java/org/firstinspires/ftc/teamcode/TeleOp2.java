package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp2", group="Linear Opmode")
public class  TeleOp2 extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor armVert = null;

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

        leftHand = hardwareMap.get(Servo.class, "left_hand");

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

        double minposL = 0.4, maxposL = 0.73, minposR = 0.72, maxposR = 0.95;

        double gripposL = 0.7, gripposR = 0.65;
        double adjustedPos = maxposR;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double leftPower;
            double rightPower;
            double armVertPower;
            

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.

            double drive = gamepad1.left_stick_y;
            double turn  =  -gamepad1.right_stick_x;

            leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

            //leftPower  = gamepad1.left_stick_y ;
            //rightPower = gamepad1.right_stick_y ;

            //set armvertpower
            if (gamepad1.left_trigger > 0) {
                armVertPower = 1;
            }
            else if (gamepad1.right_trigger > 0) {
                armVertPower = -0.75;
            }
            else {
                armVertPower = 0.08;
            }

            //open the gripper on X button if not already at most open position.
            if (gamepad1.left_bumper && gripposR < maxposR) gripposR = gripposR + .01;

            // close the gripper on Y button if not already at the closed position.
            if (gamepad1.right_bumper && gripposR > minposR) gripposR = gripposR - .01;

            // Send power to wheels, arms, and servos
            frontLeft.setPower(0.7*leftPower);
            frontRight.setPower(0.7*rightPower);
            backLeft.setPower(0.7*leftPower);
            backRight.setPower(0.7*rightPower);
            armVert.setPower(armVertPower);

            leftHand.setPosition(gripposR);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }
}
