package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;
import java.util.List;

@Autonomous(name="BioBuzzAutonHard", group="StarterBot")
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
    private DcMotorEx launcher = null;
    private DcMotor intake = null;
    //private CRServo leftIntakeServo = null;
    //private CRServo rightIntakeServo = null;
    private CRServo windmillServo = null;

    public int LAUNCHER_TARGET_VELOCITY = 400 ;

    // Timer for precise sequencing
    private ElapsedTime runtime = new ElapsedTime();

    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {
        // Initialize the AprilTag processor
        aprilTag = new AprilTagProcessor.Builder()
                // By default, the SDK targets the 36h11 family used in FTC
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .build();

        // Initialize the Vision Portal with the Logitech C920
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "webcam 1"))
                .addProcessor(aprilTag)
                .build();

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


        while (opModeIsActive()) {
            // Retrieve the list of currently visible AprilTags
            List<AprilTagDetection> currentDetections = aprilTag.getDetections();
            telemetry.addData("# Tags Detected", currentDetections.size());

            // Iterate through every detected tag
            for (AprilTagDetection baseDetection : currentDetections) {

                // Check if the detection is a standard Single AprilTag
                if (baseDetection instanceof AprilTagSingleDetection) {
                    // Cast the base detection to the Single Detection subclass
                    AprilTagSingleDetection detection = (AprilTagSingleDetection) baseDetection;

                    if (detection.metadata != null) {
                        // Use .id (without parentheses) on the casted variable
                        telemetry.addLine(String.format("\n==== (ID %d) ====", detection.id));

                        telemetry.addLine(String.format("XYZ: %6.1f %6.1f %6.1f  (inch)",
                                detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                        telemetry.addLine(String.format("PRY: %6.1f %6.1f %6.1f  (deg)",
                                detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));

                        // Execute path logic using the clean public id variable
                        if (detection.id == 11) {
                            telemetry.addLine("Action: Target Tag 11 detected! Execute path A.");
                        }
                    } else {
                        telemetry.addLine(String.format("\nUnknown Tag ID %d (No Metadata)", detection.id));
                    }
                }
            }

            telemetry.update();
            sleep(20); // Minor delay to keep the loop readable
        }



        // Clean up resources and close the stream when done
        visionPortal.close();

        // Retract the feeder and power down the launcher
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