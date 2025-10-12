package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name = "PathingTest LM1", group = "Autonomous")
public class PathingTest extends LinearOpMode {

    @Override
    public void runOpMode() {

        Pose2d initialPose = new Pose2d(-61, -36.3, Math.toRadians(270));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        Action scorePreload = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .build();

        // Set 1
        Action prepareSet1 = drive.actionBuilder(new Pose2d(-24, -24, Math.toRadians(225))) // ends of scorePreload
                .strafeToLinearHeading(new Vector2d(-12, -29), Math.toRadians(270),
                        new TranslationalVelConstraint(75)) // prepareSet1Pose
                .build();

        Action grabSet1 = drive.actionBuilder(new Pose2d(-12, -29, Math.toRadians(270))) // ends of prepareSet1
                .strafeToLinearHeading(new Vector2d(-12, -53), Math.toRadians(270),
                        new TranslationalVelConstraint(20)) // grabSet1Pose
                .build();

        Action scoreSet1 = drive.actionBuilder(new Pose2d(-12, -53, Math.toRadians(270))) // ends of grabSet1
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .build();

        // Set 2
        Action prepareSet2 = drive.actionBuilder(new Pose2d(-24, -24, Math.toRadians(225))) // ends of scoreSet1
                .strafeToLinearHeading(new Vector2d(12, -29), Math.toRadians(270),
                        new TranslationalVelConstraint(75)) // prepareSet2Pose
                .build();

        Action grabSet2 = drive.actionBuilder(new Pose2d(12, -29, Math.toRadians(270))) // ends of prepareSet2
                .strafeToLinearHeading(new Vector2d(12, -53), Math.toRadians(270),
                        new TranslationalVelConstraint(20)) // grabSet2Pose
                .build();

        Action scoreSet2 = drive.actionBuilder(new Pose2d(12, -53, Math.toRadians(270))) // ends of grabSet2
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .build();

        // Set 3
        Action prepareSet3 = drive.actionBuilder(new Pose2d(-24, -24, Math.toRadians(225))) // ends of scoreSet2
                .strafeToLinearHeading(new Vector2d(36.5, -29), Math.toRadians(270),
                        new TranslationalVelConstraint(75)) // prepareSet3Pose
                .build();

        Action grabSet3 = drive.actionBuilder(new Pose2d(36.5, -29, Math.toRadians(270))) // ends of prepareSet3
                .strafeToLinearHeading(new Vector2d(36.5, -53), Math.toRadians(270),
                        new TranslationalVelConstraint(20)) // grabSet3Pose
                .build();

        Action scoreSet3 = drive.actionBuilder(new Pose2d(36.5, -53, Math.toRadians(270))) // ends of grabSet3
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .build();

        // Park
        Action park = drive.actionBuilder(new Pose2d(-24, -24, Math.toRadians(225))) // ends of scoreSet3
                .strafeToLinearHeading(new Vector2d(-48, -24), Math.toRadians(180),
                        new TranslationalVelConstraint(75)) // parkPose
                .build();


        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                        scorePreload,

                        prepareSet1, grabSet1, scoreSet1,
                        prepareSet2, grabSet2, scoreSet2,
                        prepareSet3, grabSet3, scoreSet3,

                        park
                )
        );
    }
}
