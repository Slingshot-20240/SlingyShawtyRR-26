package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class BackSideAuton {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(65, 65, Math.toRadians(180), Math.toRadians(180), 14.8)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(61.5, -9, Math.toRadians(180)))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(210))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(36, -27), Math.toRadians(270), new TranslationalVelConstraint(78))
                .strafeToLinearHeading(new Vector2d(34, -61.5), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(210))
                .waitSeconds(2)

                //park
                .strafeToLinearHeading(new Vector2d(35, -20), Math.toRadians(180))





                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();

    }
}