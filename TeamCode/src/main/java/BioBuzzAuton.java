package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Autonomous(name="BioBuzzAuton", group="Robot")
public class BioBuzzAuton extends LinearOpMode {

    // Declare drive motors
    //private DcMotor leftFront  = null;
    //private DcMotor rightFront = null;
    //private DcMotor leftBack   = null;
    //private DcMotor rightBack  = null;
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotorEx launcher = null;
    private DcMotor intake = null;
    private CRServo leftIntakeServo = null;
    private CRServo rightIntakeServo = null;
    private CRServo windmillServo = null;

    @Override
    public void runOpMode() {
        // Initialize the hardware variables
        //leftFront  = hardwareMap.get(DcMotor.class, "leftFront");
        //rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        //leftBack   = hardwareMap.get(DcMotor.class, "leftBack");
        //rightBack  = hardwareMap.get(DcMotor.class, "rightBack");
        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");
        intake = hardwareMap.get(DcMotor.class, "intake");
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        windmillServo = hardwareMap.get(CRServo.class, "windmillServo");
        leftIntakeServo = hardwareMap.get(CRServo.class, "left_intake_servo");
        rightIntakeServo = hardwareMap.get(CRServo.class, "right_intake_servo");

        // Reverse the right side motors so positive power moves the robot forward
        //leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        //leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        //rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        //rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        if (opModeIsActive()) {
            // To strafe LEFT with Mecanum wheels:
            // LF: Reverse, LB: Forward, RF: Forward, RB: Reverse
            leftFrontDrive.setPower(-0.5);
            leftBackDrive.setPower(0.5);
            rightFrontDrive.setPower(0.5);
            rightBackDrive.setPower(-0.5);

            // Keep moving for a specific time duration (in milliseconds)
            // Adjust this sleep time until the robot travels exactly 2 feet
            sleep(1500);

            // Stop all motors
            leftFrontDrive.setPower(0);
            leftBackDrive.setPower(0);
            rightFrontDrive.setPower(0);
            rightBackDrive.setPower(0);

            sleep(1000);

            //Reverse
            leftFrontDrive.setPower(-0.5);
            leftBackDrive.setPower(-0.5);
            rightFrontDrive.setPower(-0.5);
            rightBackDrive.setPower(-0.5);

            sleep(1000);

            telemetry.addData("Status", "Path Complete");
            telemetry.update();
        }
    }
}