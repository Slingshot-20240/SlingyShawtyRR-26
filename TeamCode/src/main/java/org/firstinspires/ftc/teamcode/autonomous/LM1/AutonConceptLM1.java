package org.firstinspires.ftc.teamcode.autonomous.LM1;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//Subsystems Imports
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

@Config
@Autonomous(name = "LM1 Auton 67", group = "Autonomous")
public class AutonConceptLM1 extends LinearOpMode {


    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(-63.4, -16, Math.toRadians(270));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        Intake intake = new Intake(hardwareMap);
        Transfer transfer = new Transfer(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);

//-----------------Pathing Actions-----------------\\
    //Score Preload
        Action scorePreload = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .build();

    //Set 1
        Action prepareSet1 = drive.actionBuilder(new Pose2d(-24, -24, Math.toRadians(225))) // ends of scorePreload
                .strafeToLinearHeading(new Vector2d(-12, -29), Math.toRadians(270))
                .build();

        Action grabSet1 = drive.actionBuilder(new Pose2d(-12, -29, Math.toRadians(270))) // ends of prepareSet1
                .strafeToLinearHeading(new Vector2d(-12, -55), Math.toRadians(270))
                .build();

        Action scoreSet1 = drive.actionBuilder(new Pose2d(-12, -55, Math.toRadians(270))) // ends of grabSet1
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .build();

    //Set 2
        Action prepareSet2 = drive.actionBuilder(new Pose2d(-24, -24, Math.toRadians(225))) // ends of scoreSet1
                .strafeToLinearHeading(new Vector2d(12, -29), Math.toRadians(270))
                .build();

        Action grabSet2 = drive.actionBuilder(new Pose2d(12, -29, Math.toRadians(270))) // ends of prepareSet2
                .strafeToLinearHeading(new Vector2d(12, -55), Math.toRadians(270))
                .build();

        Action scoreSet2 = drive.actionBuilder(new Pose2d(12, -55, Math.toRadians(270))) // ends of grabSet2
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .build();

    //Set 3
        Action prepareSet3 = drive.actionBuilder(new Pose2d(-24, -24, Math.toRadians(225))) // ends of scoreSet2
                .strafeToLinearHeading(new Vector2d(36.5, -29), Math.toRadians(270))
                .build();

        Action grabSet3 = drive.actionBuilder(new Pose2d(36.5, -29, Math.toRadians(270))) // ends of prepareSet3
                .strafeToLinearHeading(new Vector2d(36.5, -55), Math.toRadians(270))
                .build();

        Action scoreSet3 = drive.actionBuilder(new Pose2d(36.5, -55, Math.toRadians(270))) // ends of grabSet3
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .build();

    //Park
        Action park = drive.actionBuilder(new Pose2d(-24, -24, Math.toRadians(225))) // ends of scoreSet3
                .strafeToLinearHeading(new Vector2d(-48, -24), Math.toRadians(180))
                .build();


//-----------------Initialization-----------------\\
        Actions.runBlocking(
                new ParallelAction(
                        HardwareSequences.intakeInFor(2),
                        HardwareSequences.transferUpFor(3)
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
                                AutonSequencesLM1.scoreSet()
                        ),



                //--------Set 1--------\\
                        //Grab Set 1
                        new SequentialAction(
                                new ParallelAction(
                                        prepareSet1,
                                        AutonSequencesLM1.prepareForSet(3,5)
                                ),
                                new ParallelAction(
                                        grabSet1,
                                        AutonSequencesLM1.intakeSet()
                                )
                        ),

                        //Shoot Set 1
                        new SequentialAction(
                                scoreSet1,
                                AutonSequencesLM1.scoreSet()
                        ),



                //--------Set 2--------\\
                        //Grab Set 2
                        new SequentialAction(
                                new ParallelAction(
                                        prepareSet2,
                                        AutonSequencesLM1.prepareForSet(3,5)
                                ),
                                new ParallelAction(
                                        grabSet2,
                                        AutonSequencesLM1.intakeSet()
                                )
                        ),

                        //Shoot Set 1
                        new SequentialAction(
                                scoreSet2,
                                AutonSequencesLM1.scoreSet()
                        ),



                //--------Set 3--------\\
                        //Grab Set 3
                        new SequentialAction(
                                new ParallelAction(
                                        prepareSet3,
                                        AutonSequencesLM1.prepareForSet(3,5)
                                ),
                                new ParallelAction(
                                        grabSet3,
                                        AutonSequencesLM1.intakeSet()
                                )
                        ),

                        //Shoot Set 1
                        new SequentialAction(
                                scoreSet3,
                                AutonSequencesLM1.scoreSet()
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