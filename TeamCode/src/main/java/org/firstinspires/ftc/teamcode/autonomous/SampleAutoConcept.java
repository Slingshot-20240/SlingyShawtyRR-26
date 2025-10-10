//package org.firstinspires.ftc.teamcode.autonomous;
//
//import com.acmerobotics.dashboard.config.Config;
//import com.acmerobotics.roadrunner.Action;
//import com.acmerobotics.roadrunner.ParallelAction;
//import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.SequentialAction;
//import com.acmerobotics.roadrunner.Vector2d;
//import com.acmerobotics.roadrunner.ftc.Actions;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.teamcode.MecanumDrive;
//import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
//import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
//
//@Config
//@Autonomous(name = "Concept RR ITD_SampleAuton", group = "Autonomous")
//public class SampleAutoConcept extends LinearOpMode {
//
//
//    @Override
//    public void runOpMode() {
//        Pose2d initialPose = new Pose2d(-63, 39, Math.toRadians(90));
//        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
//
//        Intake intake = new Intake(hardwareMap);
//
//        Action scorePreload = drive.actionBuilder(initialPose)
//                .strafeToLinearHeading(new Vector2d(-58,57),Math.toRadians(315))
//                .build();
//
//        Action grabPickup1 = drive.actionBuilder(new Pose2d(-58, 57, Math.toRadians(315)))
//                .strafeToLinearHeading(new Vector2d(-35,49),Math.toRadians(0))
//                .build();
//
//        Action scorePickup1 = drive.actionBuilder(new Pose2d(-35, 49, Math.toRadians(0)))
//                .strafeToLinearHeading(new Vector2d(-58,57),Math.toRadians(315))
//                .build();
//
//        Action grabPickup2 = drive.actionBuilder(new Pose2d(-58, 57, Math.toRadians(315)))
//                .strafeToLinearHeading(new Vector2d(-29,58),Math.toRadians(0))
//                .build();
//
//        Action scorePickup2 = drive.actionBuilder(new Pose2d(-29, 58, Math.toRadians(0)))
//                .strafeToLinearHeading(new Vector2d(-58,57),Math.toRadians(315))
//                .build();
//
//        Action grabPickup3 = drive.actionBuilder(new Pose2d(-58, 57, Math.toRadians(315)))
//                .strafeToLinearHeading(new Vector2d(-23,63),Math.toRadians(0))
//                .build();
//
//        Action scorePickup3 = drive.actionBuilder(new Pose2d(-23, 63, Math.toRadians(0)))
//                .strafeToLinearHeading(new Vector2d(-58,57),Math.toRadians(315))
//                .build();
//
//        Action park = drive.actionBuilder(new Pose2d(-58,57,Math.toRadians(315)))
//                //Control Pose
//                .strafeToLinearHeading(new Vector2d(-12,32), Math.toRadians(90))
//                //Park Pose
//                .strafeToLinearHeading(new Vector2d(-12,26),Math.toRadians(90))
//                .build();
//
//        // Initialize
//        Actions.runBlocking(armClaw.close());
//
//
//        waitForStart();
//
//        if (isStopRequested()) return;
//
//
//        Actions.runBlocking(
//                new SequentialAction(
//
//        //----------Score preload----------\\
//                        new ParallelAction(
//                                scorePreload,
//                                AutonSequences.scoreHigh()
//                        ),
//                        AutonSequences.armClawScore(),
//
//
//        //----------Block 1 cycle----------\\
//                        new ParallelAction(
//                                grabPickup1,
//                                AutonSequences.readyForPickup()
//                        ),
//                        AutonSequences.pickUp(3),
//                        AutonSequences.transferBlock(),
//
//                        //Score Pickup 1
//                        new ParallelAction(
//                                scorePickup1,
//                                AutonSequences.scoreHigh()
//                        ),
//                        AutonSequences.armClawScore(),
//
//        //----------Block 2 cycle----------\\
//                        //Grab Block 2
//                        new ParallelAction(
//                                grabPickup2,
//                                AutonSequences.readyForPickup()
//                        ),
//                        AutonSequences.pickUp(3),
//                        AutonSequences.transferBlock(),
//
//                        //Score Pickup 2
//                        new ParallelAction(
//                                scorePickup2,
//                                AutonSequences.scoreHigh()
//                        ),
//                        AutonSequences.armClawScore(),
//
//        //----------Block 3 cycle----------\\
//                        //Grab Block 3
//                        new ParallelAction(
//                                grabPickup3,
//                                AutonSequences.readyForPickup()
//                        ),
//                        AutonSequences.pickUp(3),
//                        AutonSequences.transferBlock(),
//
//                        //Score Pickup 1
//                        new ParallelAction(
//                                scorePickup3,
//                                AutonSequences.scorePickup3()
//                        ),
//                        new ParallelAction(
//                                AutonSequences.armClawScore(),
//                                extendo.mini_out()
//                        ),
//
//        //----------Park----------\\
//                        new ParallelAction(
//                                park,
//                                //reset all subsystems
//                                lift.toDown(),
//                                extendo.in(),
//                                intake.idle()
//                        )
//                )
//        );
//
//
//    }
//}