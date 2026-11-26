package org.firstinspires.ftc.teamcode;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedro.Constants;

    @Autonomous(name = "PedroAuton", group = "Autonomous")
    public class PedroAuton extends LinearOpMode {


        private Follower follower;

        private final PoseFactory poseFactory = PoseFactory.degrees();

        private final Pose start = poseFactory.of(56, 8, 270);
        private final Pose path1 = poseFactory.of(9.586, 105.9973, 270);
        private final Pose path1Control1 = poseFactory.of(25.2199, 32.3021, 0);

        private DcMotorEx launcher = null;
        private DcMotor intake = null;
        private DcMotorEx windmillMotor = null;
        public int LAUNCHER_TARGET_VELOCITY = 1300;
        private ElapsedTime runtime = new ElapsedTime();

        // Autonomous routine
        public Command autoRoutine() {
            return sequential(
                    follow(follower, path1())
            );
        }

        @Override
        public void runOpMode() {
            Scheduler.reset();

            intake = hardwareMap.get(DcMotor.class, "intake");
            launcher = hardwareMap.get(DcMotorEx.class, "launcher");
            windmillMotor = hardwareMap.get(DcMotorEx.class, "windmillMotor");

            follower = Constants.create(hardwareMap);
            follower.setPose(start);
            follower.update();

            waitForStart();
            //schedule(autoRoutine());

            //Non-Pedro Stuff
            telemetry.addData("Status", "Spinning up flywheel...");
            telemetry.update();
            launcher.setVelocity(LAUNCHER_TARGET_VELOCITY);
            runtime.reset();
            //Spin up for 3 seconds
            while (opModeIsActive() && runtime.seconds() < 3.0) {
                follower.update(); //keeps localization accurate while stationary
                telemetry.addData("Launcher Velocity", launcher.getVelocity());
                telemetry.update();
            }
            intake.setPower(0.5);

            windmillMotor.setPower(0.75);
            runtime.reset();
            //Shoot Pollen for 5 seconds
            while (opModeIsActive() && runtime.seconds() < 5.0)
            {
                follower.update(); //keeps localization accurate while stationary
            }

            launcher.setPower(0);
            windmillMotor.setPower(0);
            intake.setPower(0);

            //Start Pedro Stuff
            schedule(autoRoutine());
            while (opModeIsActive() && follower.isBusy()) {
                follower.update();
                Scheduler.execute();

                telemetry.addData("x", follower.pose().x());
                telemetry.addData("y", follower.pose().y());
                telemetry.addData("heading", follower.pose().heading());

                if (follower.currentPath() != null) {
                    telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
                    telemetry.addData("Path number", follower.pathIndex());
                }

                telemetry.update();
            }

        }

        public Path path1() {
            return Paths.curve(start, path1Control1, path1).linear(start, path1);
        }
    }

