package org.firstinspires.ftc.teamcode.autonomous.LM1;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//Subsystems Imports
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.shooter.action.HoodAction;
import org.firstinspires.ftc.teamcode.subsystems.shooter.action.ShooterAction;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

@Disabled
@Config
@Autonomous(name = "6 Blue FAR Auton", group = "Autonomous")
public class LM1BlueFarAuton extends LinearOpMode {


    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(61.5, -14, Math.toRadians(180));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        LM1FarSequences acl = new LM1FarSequences(hardwareMap);
        LM1HardwareSequences hws = new LM1HardwareSequences(hardwareMap);

        Intake intake = new Intake(hardwareMap);
        Transfer transfer = new Transfer(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);

//-----------------Pathing Actions-----------------\\
        // Score Preload
        Action scorePreload = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(201.04))
                .build();
        // Set 1
        Action prepareSet1 = drive.actionBuilder(new Pose2d(55, -12, Math.toRadians(201.04))) // ends of scorePreload
                .strafeToLinearHeading(new Vector2d(34, -27), Math.toRadians(270)) // prepareSet1Pose
                .build();

        Action grabSet1 = drive.actionBuilder(new Pose2d(34, -27, Math.toRadians(270))) // ends of prepareSet1
                .strafeToLinearHeading(new Vector2d(34, -61.5), Math.toRadians(270),
                        new TranslationalVelConstraint(78))
                .build();

        Action scoreSet1 = drive.actionBuilder(new Pose2d(34, -61.5, Math.toRadians(270))) // ends of grabSet1
                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(204))
                .build();


        // Park
        Action park = drive.actionBuilder(new Pose2d(55, -12, Math.toRadians(204))) // ends of scoreSet3
                .strafeToLinearHeading(new Vector2d(35, -20), Math.toRadians(180)) // parkPose
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
                                acl.preparePreloads()
                        ),
                        acl.scorePreloads(),



                        //--------Set 1--------\\
                        //Grab Set 1
                        new SequentialAction(
                                prepareSet1,

                                new ParallelAction(
                                        //new HoodAction(shooter.variableHood,0.3),
                                        grabSet1,
                                        acl.intakeSet(),
                                        //start spinning up shooter
                                        new ShooterAction(shooter.outtake, -1390)
                                )
                        ),

                        //Shoot Set 1
                        new SequentialAction(
                                scoreSet1,
                                acl.scoreSet()
                        ),
                        park
                )
        );

    }
}