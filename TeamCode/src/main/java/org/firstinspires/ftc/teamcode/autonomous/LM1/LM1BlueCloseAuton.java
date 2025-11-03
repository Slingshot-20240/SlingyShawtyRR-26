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
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

@Disabled
@Config
@Autonomous(name = "Blue CLOSE Auton", group = "Autonomous")
public class LM1BlueCloseAuton extends LinearOpMode {


    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(-55, -45, Math.toRadians(143));

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        LM1CloseSequences acl = new LM1CloseSequences(hardwareMap);
        LM1HardwareSequences hws = new LM1HardwareSequences(hardwareMap);

        Intake intake = new Intake(hardwareMap);
        Transfer transfer = new Transfer(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);

//-----------------Pathing Actions-----------------\\
        // Score Preload
        Action scorePreload = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(-25, -25), Math.toRadians(225))
                .build();

        // Set 1
        Action prepareSet1 = drive.actionBuilder(new Pose2d(-25, -25, Math.toRadians(225))) // ends of scorePreload
                .strafeToLinearHeading(new Vector2d(-11, -22), Math.toRadians(270),
                        new TranslationalVelConstraint(70)) // prepareSet1Pose
                .build();

        Action grabSet1 = drive.actionBuilder(new Pose2d(-11, -22, Math.toRadians(270))) // ends of prepareSet1
                .strafeToLinearHeading(new Vector2d(-12, -51.5), Math.toRadians(270)) // grabSet1Pose
                .build();

        Action scoreSet1 = drive.actionBuilder(new Pose2d(-12, -51.5, Math.toRadians(270))) // ends of grabSet1
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .build();



        // Park
        Action park = drive.actionBuilder(new Pose2d(-24, -24, Math.toRadians(225))) // ends of scoreSet3
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
                                        shooter.out()
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