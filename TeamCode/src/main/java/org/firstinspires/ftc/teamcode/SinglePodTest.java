package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "Single Pod Test", group = "Test")
public class SinglePodTest extends OpMode {

    // Define as DcMotorEx to access the raw encoder hardware counter
    private DcMotorEx testPod;

    @Override
    public void init() {
        // Match this name to the active port in your Driver Station configuration
        testPod = hardwareMap.get(DcMotorEx.class, "podEncoder");

        // Reset the encoder ticks to 0 on initialization
        testPod.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        // Tell the hub to treat this port as a passive tick counter
        testPod.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Status", "Initialized. Spin the pod wheel to test.");
        telemetry.update();
    }

    @Override
    public void loop() {
        // Read raw encoder ticks directly from the Hub port
        int currentTicks = testPod.getCurrentPosition();

        // Calculate a very rough estimation of distance traveled in inches
        // (Assumes the goBILDA 48mm wheel diameter and a standard 2000-count encoder)
        double approximateInches = (currentTicks / 2000.0) * (4.8 / 2.54 * Math.PI);

        // Display data to Driver Station
        telemetry.addData("Pod Raw Ticks", currentTicks);
        telemetry.addData("Approx. Distance (Inches)", approximateInches);

        telemetry.update();
    }
}