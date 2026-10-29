package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="BioBuzzAutonEasy", group="StarterBot")
public class BioBuzzAutonEasy extends LinearOpMode {

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
    //private CRServo leftIntakeServo = null;
    //private CRServo rightIntakeServo = null;
    private CRServo windmillServo = null;

    public int LAUNCHER_TARGET_VELOCITY = 400 ;

    // Timer for precise sequencing
    private ElapsedTime runtime = new ElapsedTime();

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
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(40, 0, 0, 12.5));


        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        if (opModeIsActive()) {
            // STEP 1: Spin up the flywheel motor to full power
            telemetry.addData("Status", "Spinning up flywheel...");
            telemetry.update();
            //launcher.setPower(1.0);
            launcher.setVelocity(LAUNCHER_TARGET_VELOCITY);
            runtime.reset();
            while (opModeIsActive() && runtime.seconds() < 3.0) {
                telemetry.addData("Launcher Velocity", launcher.getVelocity());
                telemetry.update();
            }
            windmillServo.setPower(0.5);
            intake.setPower(0.5);



                // STEP 2: Actuate the feeder servo to push the game piece into the launcher
                telemetry.addData("Status", "Firing single shot!");
                telemetry.update();
                //feederServo.setPosition(SERVO_PUSH);

                //1
                runtime.reset();
                while (opModeIsActive() && runtime.seconds() < 1.3) //15.0) {  }
                windmillServo.setPower(0.0);
            runtime.reset();
                while (opModeIsActive() && runtime.seconds() < 0.4) //15.0) { }


                    //2
                windmillServo.setPower(0.5);
            runtime.reset();
                while (opModeIsActive() && runtime.seconds() < 1.3) //15.0) { }
                windmillServo.setPower(0.0);
            runtime.reset();
                while (opModeIsActive() && runtime.seconds() < 0.4) //15.0) { }

                    //3
                windmillServo.setPower(0.5);
            runtime.reset();
                while (opModeIsActive() && runtime.seconds() < 1.3) //15.0) { }
                windmillServo.setPower(0.0);
            runtime.reset();
                while (opModeIsActive() && runtime.seconds() < 0.4) //15.0) { }

                    //4
                windmillServo.setPower(0.5);
            runtime.reset();
                while (opModeIsActive() && runtime.seconds() < 1.3) //15.0) { }
                windmillServo.setPower(0.0);
            runtime.reset();
                while (opModeIsActive() && runtime.seconds() < 0.4) //15.0) { }
                    //runtime.reset();

                windmillServo.setPower(0.0);

                runtime.reset();

                launcher.setPower(0);
                windmillServo.setPower(0);
                intake.setPower(0);
                runtime.reset();

                //Backward
                leftFrontDrive.setPower(-0.2);
                leftBackDrive.setPower(-0.2);
                rightFrontDrive.setPower(-0.2);
                rightBackDrive.setPower(-0.2);
                while (opModeIsActive() && runtime.seconds() < 0.5) {
                    // Idle waiting for shot execution
                }
                runtime.reset();

                // Stop for half second
                leftFrontDrive.setPower(0);
                leftBackDrive.setPower(0);
                rightFrontDrive.setPower(0);
                rightBackDrive.setPower(0);
                while (opModeIsActive() && runtime.seconds() < 0.5) {
                    // Idle waiting for shot execution
                }
                runtime.reset();


                //Strafe left
                leftFrontDrive.setPower(0.3);
                leftBackDrive.setPower(-0.3);
                rightFrontDrive.setPower(-0.3);
                rightBackDrive.setPower(0.3);
                while (opModeIsActive() && runtime.seconds() < 0.85) {
                    // Idle waiting for shot execution
                }
                runtime.reset();

                // Stop for half second
                leftFrontDrive.setPower(0);
                leftBackDrive.setPower(0);
                rightFrontDrive.setPower(0);
                rightBackDrive.setPower(0);
                while (opModeIsActive() && runtime.seconds() < 0.5) {
                    // Idle waiting for shot execution
                }
                runtime.reset();

                //Backward again
                leftFrontDrive.setPower(-0.3);
                leftBackDrive.setPower(-0.3);
                rightFrontDrive.setPower(-0.3);
                rightBackDrive.setPower(-0.3);
                while (opModeIsActive() && runtime.seconds() < 0.5) {
                    // Idle waiting for shot execution
                }
                runtime.reset();

                // STEP 3: Retract the feeder and power down the launcher
                //feederServo.setPosition(SERVO_RETRACTED);
                launcher.setPower(0.0);
                windmillServo.setPower(0.0);
                intake.setPower(0.0);

                telemetry.addData("Status", "Execution complete. Flywheel coasting.");
                telemetry.update();

                // Keep the OpMode alive briefly to let the servo fully reset
                runtime.reset();
                while (opModeIsActive() && runtime.seconds() < 1.0) {
                    // Safe coast down time
                }
            }




    }
}