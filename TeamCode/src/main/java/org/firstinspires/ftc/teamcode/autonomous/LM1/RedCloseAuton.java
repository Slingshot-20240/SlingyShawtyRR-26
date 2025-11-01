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
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

@Config
@Autonomous(name = "RED LM1 Auton 67", group = "Autonomous")
public class RedCloseAuton extends LinearOpMode {

    @Override
    public void runOpMode() {
        // Flip Y and heading signs
        Pose2d initialPose = new Pose2d(-55, 45, Math.toRadians(216));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        AutonSequencesLM1 acl = new AutonSequencesLM1(hardwareMap);
        HardwareSequences hws = new HardwareSequences(hardwareMap);

        Intake intake = new Intake(hardwareMap);
        Transfer transfer = new Transfer(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);

        //-----------------Pathing Actions (Y flipped)-----------------\\

        // Score Preload
        Action scorePreload = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(-25, 25), Math.toRadians(-225))
                .build();

        // Set 1
        Action prepareSet1 = drive.actionBuilder(new Pose2d(-25, 25, Math.toRadians(-225)))
                .strafeToLinearHeading(new Vector2d(-10, 22), Math.toRadians(-270),
                        new TranslationalVelConstraint(70))
                .build();

        Action grabSet1 = drive.actionBuilder(new Pose2d(-10, 22, Math.toRadians(-270)))
                .strafeToLinearHeading(new Vector2d(-12, 51.5), Math.toRadians(-270))
                .build();

        Action scoreSet1 = drive.actionBuilder(new Pose2d(-12, 51.5, Math.toRadians(-270)))
                .strafeToLinearHeading(new Vector2d(-24, 24), Math.toRadians(-225))
                .build();


        // Park
        Action park = drive.actionBuilder(new Pose2d(-24, 24, Math.toRadians(-225)))
                .strafeToLinearHeading(new Vector2d(-48, 24), Math.toRadians(-180),
                        new TranslationalVelConstraint(80))
                .build();


        //-----------------Initialization-----------------\\
        //Actions.runBlocking(new HoodAction(shooter.variableHood, 0.21));
        waitForStart();
        if (isStopRequested()) return;


        //-----------------Autonomous-----------------\\
        Actions.runBlocking(
                new SequentialAction(

                        // Preloads
                        new ParallelAction(
                                scorePreload,
                                acl.preparePreloads()
                        ),
                        acl.scorePreloads(),

                        // Set 1
                        new SequentialAction(
                                prepareSet1,
                                new ParallelAction(
                                        //new HoodAction(shooter.variableHood, 0.3),
                                        grabSet1,
                                        acl.intakeSet(),
                                        shooter.out()
                                )
                        ),
                        new SequentialAction(
                                scoreSet1,
                                acl.scoreSet()
                        ),
                        park
                )
        );
    }
}
