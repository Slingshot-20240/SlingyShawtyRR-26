package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Roadrunner.Drawing;
import org.firstinspires.ftc.teamcode.Roadrunner.Localizer;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.firstinspires.ftc.teamcode.subsystems.robot.Robot;
import org.firstinspires.ftc.teamcode.teleop.IshaanStuff.ShooterController;
import org.firstinspires.ftc.teamcode.teleop.fsm.FSM;

@TeleOp
public class ASlingTele extends OpMode {
    private GamepadMapping controls;
    private FSM fsm;
    private Robot robot;

    private MecanumDrive drive;
    private ShooterController shooter;
    String allianceColor = "red";

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, controls);
        fsm = new FSM(hardwareMap, controls);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, Math.toRadians(-270)));
        //shooterController = new ShooterController(hardwareMap);



    }

    //ISHAAN ADDED THIS INIT_LOOP FOR ALLIANCE COLOR SELECTION
    //TRY ALSO KEEPING THIS IN THE MAIN INIT LOOP idk if its gonna work
    @Override
    public void init_loop() {


        if (gamepad1.dpad_up) {
            telemetry.clear();
            telemetry.addLine("> Red (a) Alliance");
            telemetry.addLine("Blue (b) Alliance");

            allianceColor = "red";

        } else if (gamepad1.dpad_down) {
            telemetry.clear();
            telemetry.addLine("Red (a) Alliance");
            telemetry.addLine("> Blue (b) Alliance");

            allianceColor = "blue";
        }
    }

    @Override
    public void start() {
        // run once when we start
        robot.hardwareSoftReset();
    }


    @Override
    public void loop() {
        fsm.update();


//----------------------------Telemetry and Dash Field Overlay----------------------------\\

        //Direct from pinpoint position and heading
        Pose2D pinpointPose = robot.driver.getPosition();
        telemetry.addData("Pinpoint x: ", pinpointPose.getX(DistanceUnit.INCH));
        telemetry.addData("Pinpoint y: ", pinpointPose.getY(DistanceUnit.INCH));
        telemetry.addData("Pinpoint heading (deg): ", pinpointPose.getHeading(AngleUnit.DEGREES));

        //Indirect from rr, but pinpoint position and heading
        Pose2d pose = drive.localizer.getPose();
        telemetry.addData("x", pose.position.x);
        telemetry.addData("y", pose.position.y);
        telemetry.addData("heading (deg)", Math.toDegrees(pose.heading.toDouble()));

        telemetry.addData("-----------------------", "");

        //Limelight telemetry
        telemetry.addData("limelight angle", Math.toDegrees(robot.limelight.getAngle()));
        telemetry.addData("limelight nav", (robot.limelight.getLastNav()));
        telemetry.addData("limelight obelisk", (robot.limelight.getObelisk().order));

        telemetry.update();

        //Dash field overlay and draw bot with Canvas
        TelemetryPacket packet = new TelemetryPacket();
        packet.fieldOverlay().setStroke("#3F51B5");
        Drawing.drawRobot(packet.fieldOverlay(), pose);
        FtcDashboard.getInstance().sendTelemetryPacket(packet);

//----------------------------Drive Controls----------------------------\\

        //Robo Centric
        drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                        -gamepad1.left_stick_y,
                        -gamepad1.left_stick_x
                ),
                -gamepad1.right_stick_x
        ));
        drive.updatePoseEstimate();

////----------------------------Auto Park----------------------------\\
//        Action redPark = drive.actionBuilder(pose)
//                .strafeToLinearHeading(new Vector2d(38, -32.3), Math.toRadians(180))
//                .build();
//
//        Action bluePark = drive.actionBuilder(pose)
//                .strafeToLinearHeading(new Vector2d(38, 32.2), Math.toRadians(180))
//                .build();
//
//        if (gamepad1.start) {
//            if (allianceColor.equals("red")) {
//                Actions.runBlocking(redPark);
//            } else if (allianceColor.equals("blue")) {
//                Actions.runBlocking(bluePark);
//            }
//        }
//
//----------------------------Auto Align----------------------------\\
//        Action goalAlignRed = drive.actionBuilder(pose)
//                .strafeTo(new Vector2d(59,4))
//                .turnTo(Math.toRadians(-203))
//                .build();
//
//        Action goalAlignBlue = drive.actionBuilder(pose)
//                .strafeTo(new Vector2d(59,-4))
//                .turnTo(Math.toRadians(3203))
//                .build();
//
//        if (gamepad1.b) {
//            drive.localizer.setPose(new Pose2d(61.5, 0, Math.toRadians(180)));
//            //idk if this line is needed (shouldn't be needed)
//            //drive = new MecanumDrive(hardwareMap, new Pose2d(61.5, 0, Math.toRadians(180)));
//            if (allianceColor.equals("red")) {
//                Actions.runBlocking(goalAlignRed);
//            } else if (allianceColor.equals("blue")) {
//                Actions.runBlocking(goalAlignBlue);
//            }
//      }

//
//        Action turnToAprilTag = drive.actionBuilder(pose)
//            .turn(robot.limelight.getAngle())
//            .build();
//        if (controls.farLock.value()) {
//            Actions.runBlocking(turnToAprilTag);
//        }


    }
}

