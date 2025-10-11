package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(71, 71, Math.toRadians(180), Math.toRadians(180), 14.8)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-63.4, -16, Math.toRadians(270)))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))

                // Set1 Poses
                .strafeToLinearHeading(new Vector2d(-12, -29), Math.toRadians(270)) // prepareSet1Pose
                .strafeToLinearHeading(new Vector2d(-12, -55), Math.toRadians(270)) // grabSet1Pose
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))

                // Set2 Poses
                .strafeToLinearHeading(new Vector2d(12, -29), Math.toRadians(270)) // prepareSet2Pose
                .strafeToLinearHeading(new Vector2d(12, -55), Math.toRadians(270)) // grabSet2Pose
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))

                // Set3 Poses
                .strafeToLinearHeading(new Vector2d(36.5, -29), Math.toRadians(270)) // prepareSet3Pose
                .strafeToLinearHeading(new Vector2d(36.5, -55), Math.toRadians(270)) // grabSet3Pose
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))

                // Park Pose
                .strafeToLinearHeading(new Vector2d(-48, -24), Math.toRadians(180)) // parkPose


                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();

    }
}