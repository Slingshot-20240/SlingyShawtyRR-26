package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(65, 65, Math.toRadians(180), Math.toRadians(180), 14.8)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-49.3, -49, Math.toRadians(55)))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .waitSeconds(2)

                // Set1 Poses
                .strafeToLinearHeading(new Vector2d(-12, -29), Math.toRadians(270),
                        new TranslationalVelConstraint(75)) // prepareSet1Pose
                .strafeToLinearHeading(new Vector2d(-12, -53), Math.toRadians(270),
                        new TranslationalVelConstraint(20)) // grabSet1Pose
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .waitSeconds(2)

                // Set2 Poses
                .strafeToLinearHeading(new Vector2d(12, -29), Math.toRadians(270),
                        new TranslationalVelConstraint(75)) // prepareSet2Pose
                .strafeToLinearHeading(new Vector2d(12, -53), Math.toRadians(270),
                        new TranslationalVelConstraint(20)) // grabSet2Pose
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .waitSeconds(2)

                // Set3 Poses
                .strafeToLinearHeading(new Vector2d(36.5, -29), Math.toRadians(270),
                        new TranslationalVelConstraint(75)) // prepareSet3Pose
                .strafeToLinearHeading(new Vector2d(36.5, -53), Math.toRadians(270),
                        new TranslationalVelConstraint(20)) // grabSet3Pose
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .waitSeconds(2)

                // Park Pose
                .strafeToLinearHeading(new Vector2d(-48, -24), Math.toRadians(180),
                        new TranslationalVelConstraint(75)) // parkPose


                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();

    }
}