package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

//public class OneMotor extends LinearOpMode { n
@TeleOp(name = "Single Motor TeleOp", group = "Linear OpMode")
public class OneMotor extends OpMode {

    private DcMotor testMotor1 = null;
    private DcMotor testMotor2 = null;

    boolean aPressedLast = false;
    boolean bPressedLast = false;
    double power = 0.5;

    @Override
    public void init() {

        testMotor1 = hardwareMap.get(DcMotor.class, "testMotor1");
        testMotor1.setDirection(DcMotor.Direction.REVERSE);
        testMotor2 = hardwareMap.get(DcMotor.class, "testMotor2");
        testMotor2.setDirection(DcMotor.Direction.FORWARD);
        telemetry.addData("Status", "Initialized By Robo Rangers");



    }

    @Override
    //public void runOpMode()

    public void loop() {
        //if (gamepad1.a) {
        //    testMotor1.setPower(0.5); // Run at 100% power
        //} else if (gamepad1.b) {
        //    testMotor1.setPower(0.475); // Run at 60% power
        //} else {
        //    testMotor1.setPower(0.0); // Stop motor when no button is pressed
        //}
        //if (gamepad1.a) {
        //    testMotor2.setPower(0.50); // Run at 100% power
        //} else if (gamepad1.b) {
        //    testMotor2.setPower(0.475); // Run at 60% power
        //} else {
        //    testMotor2.setPower(0.0); // Stop motor when no button is pressed
        //}
            //double power = -gamepad1.left_stick_y;

            // Apply power to the motor
            //testMotor.setPower(power);


        boolean aPressedCurrent = gamepad1.a;
        if (aPressedCurrent && !aPressedLast) {
            power += 0.1;
        }
        aPressedLast = aPressedCurrent;
        boolean bPressedCurrent = gamepad1.b;
        if (bPressedCurrent && !bPressedLast) {
            power -= 0.1;
        }
        bPressedLast = bPressedCurrent;
        if (gamepad1.x) {
            testMotor2.setPower(power);
            testMotor1.setPower(power);
        }
        else
        {
            testMotor2.setPower(0);
            testMotor1.setPower(0);
        }

            // Send telemetry data to the Driver Station
            telemetry.addData("Motor 1 Power", testMotor1.getPower());
            telemetry.addData("Motor 2 Power", testMotor2.getPower());
            telemetry.addData("power variable", power);
            telemetry.addData("aPressedCurrent", aPressedCurrent);


        telemetry.update();
        //}
    }
}