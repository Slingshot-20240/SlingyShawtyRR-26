package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class LM1WithLineTo3 {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(65, 65, Math.toRadians(180), Math.toRadians(180), 14.8)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-61, -36.3, Math.toRadians(270)))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .waitSeconds(2)

                // Set1 Poses
                .strafeToLinearHeading(new Vector2d(-12, -29), Math.toRadians(270)) // prepareSet1Pose
                .strafeToLinearHeading(new Vector2d(-12, -54), Math.toRadians(270), new TranslationalVelConstraint(25)) // grabSet1Pose
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .waitSeconds(2)

                // Set2 Poses
                .strafeToLinearHeading(new Vector2d(12, -29), Math.toRadians(270)) // prepareSet2Pose
                .strafeToLinearHeading(new Vector2d(12, -54), Math.toRadians(270), new TranslationalVelConstraint(25)) // grabSet2Pose
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .waitSeconds(2)

                // Set3 Poses
                .turnTo(Math.toRadians(0)) // prepareSet3Pose
                .lineToX(36.5)
                .turnTo(Math.toRadians(270))
                .strafeTo(new Vector2d(36.5, -54),
                        new TranslationalVelConstraint(25)) // grabSet3Pose
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .waitSeconds(2)

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