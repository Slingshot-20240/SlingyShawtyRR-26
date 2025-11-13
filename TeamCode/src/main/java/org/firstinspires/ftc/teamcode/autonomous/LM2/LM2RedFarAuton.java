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
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.shooter.action.HoodAction;


@Config
@Autonomous(name = "9 Red FAR Auton hp", group = "Autonomous")
public class LM2RedFarAuton extends LinearOpMode {


    @Override
    public void runOpMode() {


        Pose2d initialPose = new Pose2d(61.5, 14, Math.toRadians(180));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        LM2FarSequences acl = new LM2FarSequences(hardwareMap);

        Intake intake = new Intake(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);



//-----------------Pathing Actions-----------------\\
        // Score Preloads
        Action scorePreload = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(55, 12), Math.toRadians(180))
                .turnTo(Math.toRadians(-203))
                .build();

        // Set 2
        Action grabSet2 = drive.actionBuilder(new Pose2d(55, 12, Math.toRadians(-203)))
                .strafeToLinearHeading(new Vector2d(40, 22), Math.toRadians(-270)) // prepareSet1Pose
                .strafeToLinearHeading(new Vector2d(40, 57), Math.toRadians(-270))
                .build();

        //WALL !!!!!!!!!!!!!!!!
        Action scoreSet2 = drive.actionBuilder(new Pose2d(40, 60, Math.toRadians(-270))) // ends of grabSet2
                .strafeToLinearHeading(new Vector2d(55, 12), Math.toRadians(-203),
                        new TranslationalVelConstraint(50), new ProfileAccelConstraint(-30,50))
                .build();


        Action grabSet4hp = drive.actionBuilder(new Pose2d(55, 12, Math.toRadians(-203)))
                .strafeToLinearHeading(new Vector2d(40, 45), Math.toRadians(0),
                        new TranslationalVelConstraint(70), new ProfileAccelConstraint(-30,50))
                .strafeToLinearHeading(new Vector2d(59, 45), Math.toRadians(15),
                        new TranslationalVelConstraint(8), new ProfileAccelConstraint(-20,40))
                .strafeTo(new Vector2d(60,30))
                .build();

        Action scoreSet4 = drive.actionBuilder(new Pose2d(60, 30, Math.toRadians(15))) // ends of grabSet2
                .strafeToLinearHeading(new Vector2d(55, 12), Math.toRadians(-203),
                        new TranslationalVelConstraint(50), new ProfileAccelConstraint(-20,40))
                .build();

        // Park                                                         WALL !!!!!!!!
        Action park = drive.actionBuilder(new Pose2d(55, 12, Math.toRadians(-203)))
                .strafeToLinearHeading(new Vector2d(35, 20), Math.toRadians(180))
                .build();


//-----------------Initialization-----------------\\
        Actions.runBlocking(
                new ParallelAction(
                        new HoodAction(shooter.variableHood, 0.3)
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
                                acl.intakeSet(1460)
                        ),
                        //TODO - Tune the time the flywheel takes to get to good speed for preload
                        acl.scoreSet(2,2.8),



                        //--------Set 2--------\\
                        //Grab Set 2
                        new ParallelAction(
                                grabSet2,
                                //SHOOTER SECOND SET SPEED
                                acl.intakeSet(1460)
                        ),

                        //Shoot Set 2
                        new SequentialAction(
                                scoreSet2,
                                //****IF 0.3 WORKS TRY 0!!!
                                acl.scoreSet(0.3,2.8)
                        ),


                        //--------Set 4--------\\
                        //Grab Set 4
                        new ParallelAction(
                                grabSet4hp,
                                //SHOOTER 3RD SET SPEED
                                acl.intakeSet(1460)
                        ),
                        //Shoot Set 2
                        new SequentialAction(
                                scoreSet4,
                                //****IF 0.3 WORKS TRY 0!!!
                                acl.scoreSet(0.3,4)
                        ),


                        //TODO - IF TIME ALLOWS, then shoot the last set

                        //---------Park---------\\
                        park


                )
        );

    }
}