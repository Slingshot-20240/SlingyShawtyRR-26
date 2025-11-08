package org.firstinspires.ftc.teamcode.autonomous.LM2;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.shooter.action.ShooterAction;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

@Config
@Autonomous(name = "9 Blue CLOSE Auton", group = "Autonomous")
public class LM2BlueCloseAuton extends LinearOpMode {


    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(-55, -45, Math.toRadians(143));

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        LM2CloseSequences acl = new LM2CloseSequences(hardwareMap);
        LM2HardwareSequences hws = new LM2HardwareSequences(hardwareMap);

        Intake intake = new Intake(hardwareMap);
        Transfer transfer = new Transfer(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);

//-----------------Pathing Actions-----------------\\
        // Score Preload
        Action scorePreload = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .build();

        // Set 1
        Action grabSet2 = drive.actionBuilder(new Pose2d(-24, -24, Math.toRadians(225))) // ends of scorePreload
                .strafeToLinearHeading(new Vector2d(-11, -22), Math.toRadians(270),
                        new TranslationalVelConstraint(70)) // prepareSet1Pose
                .strafeToLinearHeading(new Vector2d(-12, -52), Math.toRadians(270)) // grabSet1Pose
                .build();


        Action scoreSet2 = drive.actionBuilder(new Pose2d(-12, -52, Math.toRadians(270))) // ends of grabSet1
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .build();


        // Set 2
        Action grabSet3 = drive.actionBuilder(new Pose2d(-24, -24, Math.toRadians(225))) // ends of scorePreload
                .strafeToLinearHeading(new Vector2d(12, -22), Math.toRadians(270),
                        new TranslationalVelConstraint(70))
                .strafeToLinearHeading(new Vector2d(12.3, -60), Math.toRadians(270))
                .build();


        Action scoreSet3 = drive.actionBuilder(new Pose2d(-12.3, -60, Math.toRadians(270))) // ends of grabSet1
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .build();

        // Set 3
        Action grabSet4 = drive.actionBuilder(new Pose2d(-24, -24, Math.toRadians(225))) // ends of scorePreload
                .strafeToLinearHeading(new Vector2d(35, -22), Math.toRadians(270),
                        new TranslationalVelConstraint(70))
                .strafeToLinearHeading(new Vector2d(35, -60), Math.toRadians(270))
                .build();

        Action scoreSet4 = drive.actionBuilder(new Pose2d(35, -60, Math.toRadians(270))) // ends of grabSet1
                .strafeToLinearHeading(new Vector2d(-44, -24), Math.toRadians(245))
                .build();


        // Park
        Action park = drive.actionBuilder(new Pose2d(-44, -24, Math.toRadians(245))) // ends of scoreSet3
                .strafeToLinearHeading(new Vector2d(-48, -24), Math.toRadians(180),
                        new TranslationalVelConstraint(80)) // parkPose
                .build();


//-----------------Initialization-----------------\\
        Actions.runBlocking(
                new ParallelAction(
                        //new HoodAction(shooter.variableHood, 0.21)

                )
        );


        waitForStart();

//-----------------Autonomous-----------------\\
        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(

                //--------Preloads--------\\
                        //Shoot Preloads
                        new ParallelAction(
                                scorePreload,
                                //SHOOTER FIRST SET SPEED
                                acl.intakeSet(1020)
                        ),
                        //TODO - Tune the time the flywheel takes to get to good speed for preload
                        acl.scoreSet(1,3),



                //--------Set 2--------\\
                        //Grab Set 2
                        new ParallelAction(
                                grabSet2,
                                //SHOOTER SECOND SET SPEED
                                acl.intakeSet(1020)
                        ),

                        //Shoot Set 2
                        new SequentialAction(
                                scoreSet2,
                                //TODO - Flywheel is already near speed, tune the time it takes to adjust. should be very low
                                //****IF 0.1 WORKS TRY 0!!!
                                acl.scoreSet(0,3)
                        ),

                //--------Set 3--------\\
                        //Grab Set 3
                        new ParallelAction(
                                grabSet3,
                                //SHOOTER 3RD SET SPEED
                                acl.intakeSet(1020)
                        ),

                        //Shoot Set 3
                        new SequentialAction(
                                scoreSet3,
                                //TODO - Flywheel is already near speed, tune the time it takes to adjust. should be very low
                                //****IF 0.1 WORKS TRY 0!!!
                                acl.scoreSet(0,3)
                        ),

                //--------Set 4--------\\
                        //Grab Set 4
                        new ParallelAction(
                                grabSet4,
                                //SHOOTER 3RD SET SPEED
                                acl.intakeSet(970)
                        ),

                        //Shoot Set 3
                        new SequentialAction(
                                scoreSet4,
                                //TODO - Flywheel is already near speed, tune the time it takes to adjust. should be very low
                                //****IF 0.1 WORKS TRY 0!!!
                                acl.scoreSet(0,3)
                        ),

                //---------Park---------\\
                        park


                )
        );

    }
}