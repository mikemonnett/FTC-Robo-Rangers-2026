package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

//This is a test for git commit
@TeleOp(name = "Sensor: HuskyLens Iterative", group = "Iterative Opmode")
public class HuskyLensIterativeTest extends OpMode {

    private HuskyLens huskyLens;
    private ElapsedTime rateLimit = new ElapsedTime();
    private final double READ_PERIOD = 0.1; // Read every 100ms to avoid overwhelming the I2C bus

    @Override
    public void init() {
        // Retrieve the HuskyLens from the hardware map
        // Ensure "huskyLens" matches the name in your Robot Configuration
        huskyLens = hardwareMap.get(HuskyLens.class, "huskyLens");

        telemetry.addData("Status", "Connecting to HuskyLens...");
        telemetry.update();

        // Verify connection
        if (!huskyLens.knock()) {
            telemetry.addData("Error", "HuskyLens communication failed! Check wiring.");
        } else {
            telemetry.addData("Status", "HuskyLens Connected!");
        }

        // Set the HuskyLens algorithm explicitly to Tag Recognition
        huskyLens.selectAlgorithm(HuskyLens.Algorithm.TAG_RECOGNITION);

        rateLimit.reset();
    }

    @Override
    public void init_loop() {
        // Executed repeatedly after Init is pressed, before Play
    }

    @Override
    public void start() {
        // Executed once when Play is pressed
        rateLimit.reset();
    }

    @Override
    public void loop() {
        // Enforce a rate limit to keep I2C loop times stable
        if (rateLimit.seconds() >= READ_PERIOD) {
            rateLimit.reset();

            // Request the array of detected blocks from the camera
            HuskyLens.Block[] blocks = huskyLens.blocks();

            telemetry.addData("Blocks Detected", blocks.length);

            // Loop through all detected blocks and output their properties
            for (int i = 0; i < blocks.length; i++) {
                HuskyLens.Block block = blocks[i];

                // HuskyLens outputs ID 0 for AprilTags it recognizes but hasn't explicitly learned yet
                telemetry.addLine(String.format("Tag Index [%d]:", i));
                telemetry.addData("  ID", block.id);
                telemetry.addData("  Center X", block.x);
                telemetry.addData("  Center Y", block.y);
                telemetry.addData("  Width", block.width);
                telemetry.addData("  Height", block.height);
            }
        }

        // General loop monitoring telemetry
        telemetry.addData("Loop Time (ms)", getRuntime() * 1000);
        // Important: telemetry.update() is handled automatically by iterative OpMode!
    }

    @Override
    public void stop() {
        // Code to execute when the driver presses Stop
    }
}