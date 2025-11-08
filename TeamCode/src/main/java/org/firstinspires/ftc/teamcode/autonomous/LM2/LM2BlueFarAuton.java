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
import org.firstinspires.ftc.teamcode.subsystems.shooter.action.HoodAction;

@Config
@Autonomous(name = "9 Blue FAR Auton", group = "Autonomous")
public class LM2BlueFarAuton extends LinearOpMode {


    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(61.5, -14, Math.toRadians(180));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        LM2FarSequences acl = new LM2FarSequences(hardwareMap);

        Intake intake = new Intake(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);

//-----------------Pathing Actions-----------------\\
    // Score Preloads
        Action scorePreload = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(201.04))
                .build();

//USE THIS IF THE SPLINE DOESN'T WORK!! - Strafe Method
        /*
        Action grabSet2 = drive.actionBuilder(new Pose2d(34, -27, Math.toRadians(270))) // ends of prepareSet1
                .strafeToLinearHeading(new Vector2d(34, -27), Math.toRadians(270)) // prepareSet1Pose
                .strafeToLinearHeading(new Vector2d(34, -61.5), Math.toRadians(270),
                        new TranslationalVelConstraint(78))
                .build();

         */

    // Set 2
        Action grabSet2 = drive.actionBuilder(new Pose2d(55, -12, Math.toRadians(201.04))) // ends of prepareSet1
                .splineTo(new Vector2d(36,-45),Math.toRadians(270))
                .splineTo(new Vector2d(36,-62),Math.toRadians(270))
                .build();

        //THE POSES HERE ARE MEANT TO NOT MATCH UP!!!.
        //By making the robot go to -62 very slightly past the wall, we ensure the y axis, and set it to -61.5
        Action scoreSet2 = drive.actionBuilder(new Pose2d(36, -61.5, Math.toRadians(270))) // ends of grabSet2
                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(201.04))
                .build();


    // Set 3
        Action grabSet3 = drive.actionBuilder(new Pose2d(55, -12, Math.toRadians(201.04)))
                .strafeToLinearHeading(new Vector2d(12,-22),Math.toRadians(270),
                        new TranslationalVelConstraint(70))
                .strafeToLinearHeading(new Vector2d(12.3,-62),Math.toRadians(270))
                .build();

        //THE POSES HERE ARE MEANT TO NOT MATCH UP!!!.
        //By making the robot go to -62 very slightly past the wall, we ensure the y axis, and set it to -61.5
        Action scoreSet3 = drive.actionBuilder(new Pose2d(12.3, -61.5, Math.toRadians(270)))
                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(201.04))
                .build();

    // Set 4
        Action grabSet4 = drive.actionBuilder(new Pose2d(55, -12, Math.toRadians(201.04))) // ends of scorePreload
                .strafeToLinearHeading(new Vector2d(-11, -22), Math.toRadians(270),
                        new TranslationalVelConstraint(70)) // prepareSet1Pose
                .strafeToLinearHeading(new Vector2d(-12, -52), Math.toRadians(270)) // grabSet1Pose
                .build();


        Action scoreSet4 = drive.actionBuilder(new Pose2d(-12, -52, Math.toRadians(270))) // ends of grabSet1
                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(201.04))
                .build();

        // Park
        Action park = drive.actionBuilder(new Pose2d(55, -12, Math.toRadians(201.04)))
                .strafeToLinearHeading(new Vector2d(35, -20), Math.toRadians(180))
                .build();


//-----------------Initialization-----------------\\
        Actions.runBlocking(
                new ParallelAction(
                        new HoodAction(shooter.variableHood, 0.28)
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
                                acl.intakeSet(1480)
                        ),
                        //TODO - Tune the time the flywheel takes to get to good speed for preload
                        acl.scoreSet(1,2.7),



                //--------Set 2--------\\
                        //Grab Set 2
                        new ParallelAction(
                                grabSet2,
                                //SHOOTER SECOND SET SPEED
                                acl.intakeSet(1480)
                        ),

                        //Shoot Set 2
                        new SequentialAction(
                                scoreSet2,
                                //****IF 0.3 WORKS TRY 0!!!
                                acl.scoreSet(0,2.7)
                        ),

                //--------Set 3--------\\
                        //Grab Set 3
                        new ParallelAction(
                                grabSet3,
                                //SHOOTER 3RD SET SPEED
                                acl.intakeSet(1480)
                        ),

                        //Shoot Set 3
                        new SequentialAction(
                                scoreSet3,
                                //****IF 0.3 WORKS TRY 0!!!
                                acl.scoreSet(0,2.7)
                        ),
                //--------Set 4--------\\
                        //Grab Set 4
                        new ParallelAction(
                                grabSet4,
                                //SHOOTER 3RD SET SPEED
                                acl.intakeSet(1480)
                        ),

                        //Shoot Set 4
                        new SequentialAction(
                                scoreSet4,
                                //****IF 0.3 WORKS TRY 0!!!
                                acl.scoreSet(0,2.7)
                        ),

                //---------Park---------\\
                        park


                )
        );

    }
}