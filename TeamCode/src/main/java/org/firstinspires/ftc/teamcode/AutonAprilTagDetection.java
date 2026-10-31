package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import java.util.List;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

@Autonomous(name = "Auton AprilTag Detection", group = "Concept")
@Disabled
public class AutonAprilTagDetection extends LinearOpMode {

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

        telemetry.addData("Status", "Initialized. Waiting for start...");
        telemetry.update();

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
    }
}