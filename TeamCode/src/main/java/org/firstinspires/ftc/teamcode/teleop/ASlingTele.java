package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Roadrunner.Drawing;
import org.firstinspires.ftc.teamcode.Roadrunner.Localizer;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.firstinspires.ftc.teamcode.subsystems.robot.Robot;
import org.firstinspires.ftc.teamcode.teleop.fsm.FSM;

@TeleOp
public class ASlingTele extends OpMode {
    private GamepadMapping controls;
    private FSM fsm;
    private Robot robot;

    private MecanumDrive drive;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, controls);
        fsm = new FSM(hardwareMap, controls);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        //THIS IS THE LINE THAT SCREWS UP ALL DRIVE CONTROLS, (unless the drivetrain is commented out)
        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, Math.toRadians(90)));


    }

    @Override
    public void start() {
        // run once when we start
        robot.hardwareSoftReset();
    }

    @Override
    public void loop() {
        fsm.update();


        //Localization and Parks Jawns
        drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                        -gamepad1.left_stick_y,
                        -gamepad1.left_stick_x
                ),
                -gamepad1.right_stick_x
        ));

        drive.updatePoseEstimate();

        Pose2d pose = drive.localizer.getPose();
        telemetry.addData("x", pose.position.x);
        telemetry.addData("y", pose.position.y);
        telemetry.addData("heading (deg)", Math.toDegrees(pose.heading.toDouble()));
        telemetry.update();

        TelemetryPacket packet = new TelemetryPacket();
        packet.fieldOverlay().setStroke("#3F51B5");
        Drawing.drawRobot(packet.fieldOverlay(), pose);
        FtcDashboard.getInstance().sendTelemetryPacket(packet);


        //ADDED BY ISHAAN
        Action park = drive.actionBuilder(pose)
                .strafeToLinearHeading(new Vector2d(-5,10), Math.toRadians(180))
                .build();

        if (gamepad1.a) {
            Actions.runBlocking(park);
        }


    }
}
