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

//Subsystems Imports
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.HoodAction;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

@Config
@Autonomous(name = "LM1 Auton 67", group = "Autonomous")
public class AutonConceptLM1 extends LinearOpMode {


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
        // Score Preload
        Action scorePreload = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .build();

        // Set 1
        Action prepareSet1 = drive.actionBuilder(new Pose2d(-24, -24, Math.toRadians(225))) // ends of scorePreload
                .strafeToLinearHeading(new Vector2d(-12, -24), Math.toRadians(270),
                        new TranslationalVelConstraint(75)) // prepareSet1Pose
                .build();

        Action grabSet1 = drive.actionBuilder(new Pose2d(-12, -24, Math.toRadians(270))) // ends of prepareSet1
                .strafeToLinearHeading(new Vector2d(-12, -53), Math.toRadians(270)
                ) // grabSet1Pose
                .build();

        Action scoreSet1 = drive.actionBuilder(new Pose2d(-12, -53, Math.toRadians(270))) // ends of grabSet1
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .build();

        // Set 2
        Action prepareSet2 = drive.actionBuilder(new Pose2d(-24, -24, Math.toRadians(225))) // ends of scoreSet1
                .strafeToLinearHeading(new Vector2d(12, -24), Math.toRadians(270),
                        new TranslationalVelConstraint(80)) // prepareSet2Pose
                .build();

        Action grabSet2 = drive.actionBuilder(new Pose2d(12, -24, Math.toRadians(270))) // ends of prepareSet2
                .strafeToLinearHeading(new Vector2d(12, -53), Math.toRadians(270)) // grabSet2Pose
                .build();

        Action scoreSet2 = drive.actionBuilder(new Pose2d(12, -53, Math.toRadians(270))) // ends of grabSet2
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .build();

        // Set 3
        Action prepareSet3 = drive.actionBuilder(new Pose2d(-24, -24, Math.toRadians(225))) // ends of scoreSet2
                .strafeToLinearHeading(new Vector2d(36.5, -24), Math.toRadians(270),
                        new TranslationalVelConstraint(80)) // prepareSet3Pose
                .build();

        Action grabSet3 = drive.actionBuilder(new Pose2d(36.5, -24, Math.toRadians(270))) // ends of prepareSet3
                .strafeToLinearHeading(new Vector2d(36.5, -53), Math.toRadians(270))
                .build();

        Action scoreSet3 = drive.actionBuilder(new Pose2d(36.5, -53, Math.toRadians(270))) // ends of grabSet3
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
                        new HoodAction(shooter.variableHood,0.27),
                        hws.intakeInFor(2),
                        hws.transferUpFor(3)
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



                //--------Set 2--------\\
                        //Grab Set 2
                        new SequentialAction(
                                prepareSet2,

                                new ParallelAction(
                                        grabSet2,
                                        acl.intakeSet(),
                                        //start spinning up shooter
                                        shooter.out()
                                )
                        ),

                        //Shoot Set 2
                        new SequentialAction(
                                scoreSet2,
                                acl.scoreSet()
                        ),

                //--------Set 3--------\\
                        //Grab Set 3
                        new SequentialAction(
                                prepareSet3,

                                new ParallelAction(
                                        grabSet3,
                                        acl.intakeSet(),
                                        //start spinning up shooter
                                        shooter.out()
                                )
                        ),

                        //Shoot Set 3
                        new SequentialAction(
                                scoreSet3,
                                acl.scoreSet()
                        ),

                //--------Park--------\\
                        //Park and Safety Resets
                        new ParallelAction(
                                park,
                                intake.idle(),
                                transfer.off(),
                                shooter.idle()
                        )

                )
        );

    }
}