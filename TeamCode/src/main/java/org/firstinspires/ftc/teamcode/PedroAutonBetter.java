package org.firstinspires.ftc.teamcode;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "PedroAutonBetter", group = "Autonomous")
public class PedroAutonBetter extends LinearOpMode {


    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56.5018, 6.4947, 270);
    private final Pose path1 = poseFactory.of(12.5966, 0.1232, 180);
    private final Pose path1Control1 = poseFactory.of(35.0045, 12.7329, 0);
    private final Pose point2Start = poseFactory.of(12.5966, 0.1232, 180);
    private final Pose point2 = poseFactory.of(56.0569, 140.3559, 90);
    private final Pose point2Control1 = poseFactory.of(29.1065, 97.8884, 0);
    private final Pose point3Start = poseFactory.of(56.0569, 140.3559, 90);
    private final Pose point3 = poseFactory.of(7.3384, 114.0279, 40);

    private DcMotorEx launcher = null;
    private DcMotor intake = null;
    private DcMotorEx windmillMotor = null;
    public int LAUNCHER_TARGET_VELOCITY = 1300;
    private ElapsedTime runtime = new ElapsedTime();

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                follow(follower, path2()),
                follow(follower, path3())
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

    public Path path2() {
        return Paths.curve(point2Start, point2Control1, point2).linear(point2Start, point2);
    }

    public Path path3() {
        return Paths.line(point3Start, point3).linear(point3Start, point3);
    }
}

