package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="BioBuzzAuton2", group="StarterBot")
public class BioBuzzAutonHard extends LinearOpMode {

    // Declare drive motors
    //private DcMotor leftFront  = null;
    //private DcMotor rightFront = null;
    //private DcMotor leftBack   = null;
    //private DcMotor rightBack  = null;
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    //private DcMotorEx launcher = null;
    //private DcMotor intake = null;
    //private CRServo leftIntakeServo = null;
    //private CRServo rightIntakeServo = null;
    //private CRServo windmillServo = null;

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
        //intake = hardwareMap.get(DcMotor.class, "intake");
        //launcher = hardwareMap.get(DcMotorEx.class, "launcher");
       // windmillServo = hardwareMap.get(CRServo.class, "windmillServo");
        //leftIntakeServo = hardwareMap.get(CRServo.class, "left_intake_servo");
        //rightIntakeServo = hardwareMap.get(CRServo.class, "right_intake_servo");

        // Reverse the right side motors so positive power moves the robot forward
        //leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        //leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        //rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        //rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        if (opModeIsActive()) {
            // To strafe LEFT with Mecanum wheels:
            // LF: Reverse, LB: Forward, RF: Forward, RB: Reverse
            //Move Left
            //leftFrontDrive.setPower(-0.5);
            //leftBackDrive.setPower(0.5);
            //rightFrontDrive.setPower(0.5);
            //rightBackDrive.setPower(-0.5);

            // wait for alliance to tip hive (10 seconds?)
            sleep(1000);

            double power = 0.5; // Speed between -1.0 and 1.0

// To turn right (clockwise):
// Left side moves forward (+)
// Right side moves backward (-)
            leftDrive.setPower(power);
            rightDrive.setPower(-power);

            //Shoot 4 balls:

            //Rotate right and intake 4 more pollen

            //Rotate left and shoot those pollen


            telemetry.addData("Status", "Path Complete");
            telemetry.update();
        }
    }
}