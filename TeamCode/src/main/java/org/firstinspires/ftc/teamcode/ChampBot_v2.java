package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.motors.RevRobotics20HdHexMotor;
import com.qualcomm.hardware.motors.RevRobotics40HdHexMotor;
import com.qualcomm.hardware.motors.RevRoboticsCoreHexMotor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.*;
import com.qualcomm.robotcore.hardware.ColorSensor;

// Vision imports
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


/*
 * This is NOT an opmode. This file defines all the hardware on the robot
 * and some common helper functions (stop motors, reset encoders, etc.)
 */
public class ChampBot_v2 {
    public static final Double TRIGGER_THRESHOLD = 0.5;//gamepad trigger
    //1080 = 270 degrees
    //robot variables
    public double startingEncoderCount = 0.5;
    //Drive Motors
    public DcMotorEx DriveFrontLeft; //:D
    public DcMotorEx DriveFrontRight;
    public DcMotorEx DriveBackLeft;
    public DcMotorEx DriveBackRight;
    public DcMotor ArmMotor;
    public DcMotor IntakeWheel;
    //Servos
    public Servo ArmServo;
    //Odometry Encoders
    public DcMotor encoderLeft;
    public DcMotor encoderRight;
    public DcMotor encoderAux;
    //Sensors
    //Gyro
    public BNO055IMU imu;

    //Vuforia Key
    public static final String VUFORIA_KEY = "AT3AS3b/////AAABmeb4lXVRG0y7kvB4fG/FtbKNEuekc1o6rkU8fj5JsakaaPPNIV+ZalkosfH2Zl513dKhMTyUF6YtHzQUr07sQDtKs2GbaulwZFC5yov+tEsgEHA+OWVni0f18T87qrboMBnj61MUqZP4BvjPib2R1rxEr3Cj4YS9c9RnkUESyTBI6B/gCtBFLNKh7cX9PKJnKCgkVF9qexL2eu2lRS1iNAl9VsjBel6RSckVjGEo90+DSyVMgUtew2GNk8IokXYlBHlK7MY6ju3Kb1dFyLLm0RPcXxqvA3pIPNbk8Rsuv75xhBGBRMVpgteBaaWZz6YUowvEv5YfGdTjy1kmQyzufcwqvMY038k0BzPCUGXE77Md";
    //code time! :)
    private HardwareMap hardwareMap;

    public void init(HardwareMap ahwMap) {
        hardwareMap = ahwMap;
        //configure the drive motors

        DriveFrontLeft = hardwareMap.get(DcMotorEx.class, "DriveFrontLeft");
        DriveFrontLeft.setDirection(DcMotor.Direction.FORWARD);
        DriveFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        DriveFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        DriveFrontRight = hardwareMap.get(DcMotorEx.class, "DriveFrontRight");
        DriveFrontRight.setDirection(DcMotor.Direction.REVERSE);
        DriveFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        DriveFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        DriveBackLeft = hardwareMap.get(DcMotorEx.class, "DriveBackLeft");
        DriveBackLeft.setDirection(DcMotor.Direction.FORWARD);
        DriveBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        DriveBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        DriveBackRight = hardwareMap.get(DcMotorEx.class, "DriveBackRight");
        DriveBackRight.setDirection(DcMotor.Direction.REVERSE);
        DriveBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        DriveBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        ArmMotor = hardwareMap.dcMotor.get("ArmMotor");
        ArmMotor.setDirection(DcMotor.Direction.REVERSE);
        ArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        IntakeWheel = hardwareMap.dcMotor.get("IntakeWheel");
        IntakeWheel.setDirection(DcMotor.Direction.REVERSE);
        IntakeWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        IntakeWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        ArmServo = hardwareMap.servo.get("ArmServo");

        //shadow the motors with the odo encoders
        encoderLeft = DriveFrontLeft;
        encoderRight = DriveBackRight;
        encoderAux = DriveFrontRight;

        stop();
        resetDriveEncoders();

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = (BNO055IMU) hardwareMap.get("imu");
        imu.initialize(parameters);

    }

    //a function to reset encoder
    public void resetDriveEncoders() {
        DriveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        DriveBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void enableEncoders() {
        DriveFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DriveFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DriveBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DriveBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    //the stop function

    public void stop() {
        DriveFrontLeft.setPower(0);
        DriveFrontRight.setPower(0);
        DriveBackRight.setPower(0);
        DriveBackLeft.setPower(0);
    }

    public void setAllPower(double p) {
        setMotorPower(p,p,p,p);
    }

    public void setMotorPower(double FrontL, double FrontR, double BackL, double BackR) {
        DriveFrontLeft.setPower(FrontL);
        DriveFrontRight.setPower(FrontR);
        DriveBackLeft.setPower(BackL);
        DriveBackRight.setPower(BackR);
    }
}