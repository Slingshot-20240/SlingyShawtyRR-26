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
import org.firstinspires.ftc.teamcode.subsystems.shooter.action.ShooterAction;

@Config
@Autonomous(name = "Blue FAR Auton", group = "Autonomous")
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

    // Set 2
        Action grabSet2 = drive.actionBuilder(new Pose2d(55, -12, Math.toRadians(201.04))) // ends of prepareSet1
                .splineTo(new Vector2d(36,-45),Math.toRadians(270))
                .splineTo(new Vector2d(36,-62),Math.toRadians(270))
                .build();

        //THE POSES HERE ARE MEANT TO NOT MATCH UP!!!.
        //By making the robot go to -62 very slightly past the wall, we ensure the y axis, and set it to -61.5
        Action scoreSet2 = drive.actionBuilder(new Pose2d(36, -61.5, Math.toRadians(270))) // ends of grabSet2
                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(204.04))
                .build();


    // Set 3

        Action grabSet3 = drive.actionBuilder(new Pose2d(12, -22, Math.toRadians(270)))
                .strafeToLinearHeading(new Vector2d(12,-22),Math.toRadians(270),
                        new TranslationalVelConstraint(73))
                .strafeToLinearHeading(new Vector2d(12.3,-62),Math.toRadians(270))
                .build();

        //THE POSES HERE ARE MEANT TO NOT MATCH UP!!!.
        //By making the robot go to -62 very slightly past the wall, we ensure the y axis, and set it to -61.5
        Action scoreSet3 = drive.actionBuilder(new Pose2d(-12.3, -61.5, Math.toRadians(270)))
                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(201.04))
                .build();

        // Park
        Action park = drive.actionBuilder(new Pose2d(55, -12, Math.toRadians(204.04)))
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
                        intake.in(),

                    //--------Preloads--------\\
                        //Shoot Preloads
                        new ParallelAction(
                                scorePreload,
                                //SHOOTER FIRST SET SPEED
                                new ShooterAction(shooter.outtake1, shooter.outtake2, -1387)
                        ),
                        acl.scorePreloads(),



                    //--------Set 2--------\\
                        //Grab Set 2
                        new ParallelAction(
                                grabSet2,
                                acl.intakeSet(),
                                //SHOOTER SECOND SET SPEED
                                new ShooterAction(shooter.outtake1, shooter.outtake2, -1390)
                        ),

                        //Shoot Set 1
                        new SequentialAction(
                                scoreSet2,
                                acl.scoreSet()
                        ),

                    //--------Set 3--------\\
                        //Grab Set 3
                        new ParallelAction(
                                grabSet3,
                                acl.intakeSet(),
                                //SHOOTER 3RD SET SPEED
                                new ShooterAction(shooter.outtake1, shooter.outtake2, -1390)
                        ),

                        //Shoot Set 2
                        new SequentialAction(
                                scoreSet3,
                                acl.scoreSet()
                        ),
                        park


                )
        );

    }
}