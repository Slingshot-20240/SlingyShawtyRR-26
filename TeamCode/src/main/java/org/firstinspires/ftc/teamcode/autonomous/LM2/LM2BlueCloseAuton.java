package org.firstinspires.ftc.teamcode.autonomous.LM2;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.shooter.action.HoodAction;


@Config
@Autonomous(name = "9 Blue CLOSE Auton new", group = "Autonomous")
public class LM2BlueCloseAuton extends LinearOpMode {


    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(-46.7, -51, Math.toRadians(143+90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        LM2CloseSequences acl = new LM2CloseSequences(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);


//-----------------Pathing Actions-----------------\\
        // Score Preload
        Action scorePreload = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(-25, -25), Math.toRadians(225))
                .build();

        // Set 1
        Action grabSet2 = drive.actionBuilder(new Pose2d(-25, -25, Math.toRadians(225))) // ends of scorePreload
                .strafeToLinearHeading(new Vector2d(-11, -22), Math.toRadians(270),
                        new TranslationalVelConstraint(70)) // prepareSet1Pose
                .strafeToLinearHeading(new Vector2d(-12, -56), Math.toRadians(270)) // grabSet1Pose
                .build();


        Action scoreSet2 = drive.actionBuilder(new Pose2d(-12, -56, Math.toRadians(270))) // ends of grabSet1
                .strafeToLinearHeading(new Vector2d(-25, -25), Math.toRadians(225))
                .build();


        // Set 2
        Action grabSet3 = drive.actionBuilder(new Pose2d(-25, -25, Math.toRadians(225))) // ends of scorePreload
                .strafeToLinearHeading(new Vector2d(12.4, -22), Math.toRadians(270),
                        new TranslationalVelConstraint(85))
                .strafeToLinearHeading(new Vector2d(12.4, -66), Math.toRadians(270),
                        new TranslationalVelConstraint(85))
                .strafeToLinearHeading(new Vector2d(12.4, -55), Math.toRadians(270),
                        new TranslationalVelConstraint(85))
                .build();


        Action scoreSet3 = drive.actionBuilder(new Pose2d(12.4, -55, Math.toRadians(270))) // ends of grabSet1
                .strafeToLinearHeading(new Vector2d(-25, -25), Math.toRadians(225))
                .build();

        // Set 3
        Action grabSet4 = drive.actionBuilder(new Pose2d(-25, -25, Math.toRadians(225))) // ends of scorePreload
                .strafeToLinearHeading(new Vector2d(35.5, -25), Math.toRadians(270),
                        new TranslationalVelConstraint(78))

                //Spline Method
                //.strafeToLinearHeading(new Vector2d(35.6, -41), Math.toRadians(270))

                //Strafe Method
                .strafeToLinearHeading(new Vector2d(36, -66), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(36, -56), Math.toRadians(270))


                .build();

        Action scoreSet4 = drive.actionBuilder(new Pose2d(36, -56, Math.toRadians(270))) // ends of grabSet1
                //Spline Method
                //.splineToLinearHeading(new Pose2d(-44,-25, Math.toRadians(245)), Math.toRadians(167))

                //Strafe Method
                .strafeToLinearHeading(new Vector2d(-44, -25), Math.toRadians(245))
                .build();



//-----------------Initialization-----------------\\
        Actions.runBlocking(
                new ParallelAction(
                        new HoodAction(shooter.variableHood, 0.4)

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
                                acl.intakeSet(1080)
                        ),
                        //TODO - Tune the time the flywheel takes to get to good speed for preload
                        acl.scoreSet(0.5,2.3),


                //--------Set 2--------\\
                        //Grab Set 2
                        new ParallelAction(
                                grabSet2,
                                //SHOOTER SECOND SET SPEED
                                acl.intakeSet(1080)
                        ),

                        //Shoot Set 2
                        new SequentialAction(
                                scoreSet2,
                                //TODO - Flywheel is already near speed, tune the time it takes to adjust. should be very low
                                //****IF 0.1 WORKS TRY 0!!!
                                acl.scoreSet(0,2.5)
                        ),

                //--------Set 3--------\\
                        //Grab Set 3
                        new ParallelAction(
                                grabSet3,
                                //SHOOTER 3RD SET SPEED
                                acl.intakeSet(1080)
                        ),

                        //Shoot Set 3
                        new SequentialAction(
                                scoreSet3,
                                //****IF 0.1 WORKS TRY 0!!!
                                acl.scoreSet(0,2.5)
                        ),

                //--------Set 4--------\\
                        //Grab Set 4
                        new ParallelAction(
                                grabSet4,
                                //SHOOTER 4TH SET SPEED
                                //THIS IS A DIFF position(closer) so the speed should be less than the other ones

                                acl.intakeSet(970)
                        ),

                        //Shoot Set 3
                        new SequentialAction(
                                new ParallelAction(
                                        scoreSet4,
                                        new HoodAction(shooter.variableHood, 0.44)
                                ),
                                //****IF 0.1 WORKS TRY 0!!!
                                acl.scoreSet(0,2.7)
                        )




                )
        );

    }
}