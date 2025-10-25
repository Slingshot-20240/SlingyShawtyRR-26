package org.firstinspires.ftc.teamcode.autonomous.LM1;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//Subsystems Imports
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.HoodAction;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

@Config
@Autonomous(name = "Sequence Tests", group = "Autonomous")
public class SequenceTests extends LinearOpMode {


    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(-49.3, -49, Math.toRadians(55));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        AutonSequencesLM1 acl = new AutonSequencesLM1(hardwareMap);
        HardwareSequences hws = new HardwareSequences(hardwareMap);

        Intake intake = new Intake(hardwareMap);
        Transfer transfer = new Transfer(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);

//-----------------Pathing Actions-----------------\\

//double check heading stuff to make sure robot goes straight
        Action path1 = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(0, 60), Math.toRadians(90), new TranslationalVelConstraint(80))
                .strafeToLinearHeading(new Vector2d(0,0),Math.toRadians(90), new TranslationalVelConstraint(80))
                .build();

        Action path2 = drive.actionBuilder(new Pose2d(0,0,Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(0,40),Math.toRadians(0), new TranslationalVelConstraint(65))
                .build();
        Action path3 = drive.actionBuilder(new Pose2d(0,40,Math.toRadians(0)))
                .strafeToLinearHeading(new Vector2d(0,0),Math.toRadians(90))
                .build();

        Action path1a = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(0, 60), Math.toRadians(90), new TranslationalVelConstraint(80))
                .strafeToLinearHeading(new Vector2d(0,0),Math.toRadians(90), new TranslationalVelConstraint(80))
                .build();

        Action path2a = drive.actionBuilder(new Pose2d(0,0,Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(0,40),Math.toRadians(0), new TranslationalVelConstraint(65))
                .build();
        Action path3a = drive.actionBuilder(new Pose2d(0,40,Math.toRadians(0)))
                .strafeToLinearHeading(new Vector2d(0,0),Math.toRadians(90))
                .build();


        

//-----------------Initialization-----------------\\
        Actions.runBlocking(
                new SequentialAction(
                    new HoodAction(shooter.variableHood, 0.7)

                )
        );

        waitForStart();

//-----------------Autonomous-----------------\\
        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                path1,
                                acl.intakeSet()
                        ),
                        //new SleepAction(2),

                        new ParallelAction(
                                path2,
                                acl.prepareForSet()
                        ),
                        //new SleepAction(2),
                        new SequentialAction(
                                acl.scoreSet()
                        ),
                        path3,
                        new SleepAction(3),

//AGAIN
                        new ParallelAction(
                                path1a,
                                acl.intakeSet()
                        ),
                        //new SleepAction(2),

                        new ParallelAction(
                                path2a,
                                acl.prepareForSet()
                        ),
                        //new SleepAction(2),
                        new SequentialAction(
                                acl.scoreSet()
                        ),
                        path3a,
                        new SleepAction(3)




                )
        );

    }
}
