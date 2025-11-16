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
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.shooter.action.HoodAction;


@Config
@Autonomous(name = "12 red close gate", group = "Autonomous")
public class LM2RedCloseGate12 extends LinearOpMode {


    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(-46.7, 51, Math.toRadians(37+90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        LM2CloseSequences acl = new LM2CloseSequences(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);


//-----------------Pathing Actions-----------------\\
        // Score Preload
        Action scorePreload = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(-25, 25), Math.toRadians(-225))
                .build();

        // Set 1
        Action grabSet2 = drive.actionBuilder(new Pose2d(-25, 25, Math.toRadians(-225))) // ends of scorePreload
                .strafeToLinearHeading(new Vector2d(-11.5, 19), Math.toRadians(-270)) // prepare set 2
                .strafeToLinearHeading(new Vector2d(-11.5, 54.5), Math.toRadians(-270), null,
                        new ProfileAccelConstraint(-80,80))
                .build();

                                                                         //WALL
        Action gate = drive.actionBuilder(new Pose2d(-11.5, 55, Math.toRadians(-270))) // ends of scorePreload
                .strafeToLinearHeading(new Vector2d(-4.5,50),Math.toRadians(180), null,
                        new ProfileAccelConstraint(-80,80))
                .strafeTo(new Vector2d(-4.5,61), null,
                        new ProfileAccelConstraint(-80,80))
                .waitSeconds(1)
                .build();


        Action scoreSet2 = drive.actionBuilder(new Pose2d(-4.5, 55, Math.toRadians(180))) // ends gate
                .strafeToLinearHeading(new Vector2d( -25, 25), Math.toRadians(-235))
                .build();


        // Set 2
        Action grabSet3 = drive.actionBuilder(new Pose2d(-25, 25, Math.toRadians(-225))) // ends of score set 2
                .strafeToLinearHeading(new Vector2d(13, 19), Math.toRadians(-270), //prepare set 3
                        new TranslationalVelConstraint(80))
                .strafeToLinearHeading(new Vector2d(13, 63), Math.toRadians(-270), //pickup set 3
                        new TranslationalVelConstraint(80))
                .build();

                                                                            //WALL !!!!!!!
        Action drawBackSet3 = drive.actionBuilder(new Pose2d(13, 61.5, Math.toRadians(-270))) // end of grab set 3
                .strafeToLinearHeading(new Vector2d(13, 40), Math.toRadians(-270), //draw back set 3
                        new TranslationalVelConstraint(85))
                .build();


        Action scoreSet3 = drive.actionBuilder(new Pose2d(13, 40, Math.toRadians(-270))) // end of draw back set 3
                .strafeToLinearHeading(new Vector2d(-25, 25), Math.toRadians(-225))
                .build();

        // Set 3
        Action grabSet4 = drive.actionBuilder(new Pose2d(-25, 25, Math.toRadians(-225))) // end of score set 3
                .strafeToLinearHeading(new Vector2d(35.5, 19), Math.toRadians(-270))
                .strafeToLinearHeading(new Vector2d(36, 63), Math.toRadians(-270))
                .build();

                                                                          //WALL !!!!!!!
        Action drawBackSet4 = drive.actionBuilder(new Pose2d(36, 61.5, Math.toRadians(-270))) // end of grab set 4
                .strafeToLinearHeading(new Vector2d(36, 40), Math.toRadians(-270)) //draw back set 3
                .build();

        Action scoreSet4 = drive.actionBuilder(new Pose2d(36, 40, Math.toRadians(-270))) // end of draw back set 4
                .strafeToLinearHeading(new Vector2d(-58, 16), Math.toRadians(-280))
                .build();



//-----------------Initialization-----------------\\
        Actions.runBlocking(
                new ParallelAction(
                        new HoodAction(shooter.variableHood, 0.42)

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
                                acl.intakeSet(1080)
                        ),
                        //TODO - Tune the time the flywheel takes to get to good speed for preload
                        acl.scoreSet(0.5,2.4),


                        //--------Set 2--------\\
                        //Grab Set 2
                        new ParallelAction(
                                new SequentialAction(
                                        grabSet2,
                                        gate
                                ),
                                //SHOOTER SECOND SET SPEED
                                acl.intakeSet(1080)
                        ),

                        //Shoot Set 2
                        new SequentialAction(
                                scoreSet2,
                                //TODO - Flywheel is already near speed, tune the time it takes to adjust. should be very low
                                acl.scoreSet(0,2.6)
                        ),

                        //--------Set 3--------\\
                        //Grab Set 3
                        new ParallelAction(
                                new SequentialAction(
                                        grabSet3,
                                        drawBackSet3
                                ),
                                //SHOOTER 3RD SET SPEED
                                acl.intakeSet(1080)
                        ),

                        //Shoot Set 3
                        new SequentialAction(
                                scoreSet3,
                                acl.scoreSet(0,2.8)
                        ),

                        //--------Set 4--------\\
                        //Grab Set 4
                        new ParallelAction(
                                new SequentialAction(
                                        grabSet4,
                                        drawBackSet4
                                ),
                                //SHOOTER 4TH SET SPEED
                                acl.intakeSet(990)
                        ),

                        //Shoot Set 3
                        new SequentialAction(
                                new ParallelAction(
                                        scoreSet4,
                                        new HoodAction(shooter.variableHood, 0.52)
                                ),
                                acl.scoreSet(0,4)
                        )




                )
        );

    }
}