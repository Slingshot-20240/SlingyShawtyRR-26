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
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

@Config
@Autonomous(name = "LM2 Sequence Tests", group = "Autonomous")
public class LM2SequenceTests extends LinearOpMode {


    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        LM2CloseSequences acl = new LM2CloseSequences(hardwareMap);
        LM2HardwareSequences hws = new LM2HardwareSequences(hardwareMap);

        Intake intake = new Intake(hardwareMap);
        Transfer transfer = new Transfer(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);

//-----------------Pathing Actions-----------------\\

//double check heading stuff to make sure robot goes straight
        Action prepareSet = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(-10, 44), Math.toRadians(0), new TranslationalVelConstraint(85))
                .build();

        Action pickupSet = drive.actionBuilder(new Pose2d(-10,44,Math.toRadians(0)))
                .strafeToLinearHeading(new Vector2d(6,44),Math.toRadians(0), new TranslationalVelConstraint(30))
                .build();


        Action scoreSet = drive.actionBuilder(new Pose2d(6,44, Math.toRadians(0)))
                .strafeToLinearHeading(new Vector2d(-5, 75), Math.toRadians(104), new TranslationalVelConstraint(85))
                .build();

        Action goBack = drive.actionBuilder(new Pose2d(-5,75, Math.toRadians(104)))
                .strafeToLinearHeading(new Vector2d(0, 7), Math.toRadians(180), new TranslationalVelConstraint(85))
                .build();


        

//-----------------Initialization-----------------\\
        Actions.runBlocking(
                new SequentialAction(
                        new HoodAction(shooter.variableHood, 0.35)

                )
        );

        waitForStart();

//-----------------Autonomous-----------------\\
        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(

                        new ParallelAction(
                                new SequentialAction(
                                        prepareSet,
                                        pickupSet
                                ),
                                //SHOOTER FIRST SET SPEED
                                acl.intakeSet(1500)
                        ),
                        new SequentialAction(
                                scoreSet,
                                acl.scoreSet(0,4)
                        ),
                        //TODO - Tune the time the flywheel takes to get to good speed for preload

                        goBack


                )
        );

    }
}
