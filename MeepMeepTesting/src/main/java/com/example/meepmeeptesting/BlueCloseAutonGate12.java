package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class BlueCloseAutonGate12 {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(80, 70, Math.toRadians(180), Math.toRadians(180), 14.8)
                .build();

        //90 - 53 = 47. 47 + 180 will get you the red starting pos.
        //53 + 90 will get you the blue starting pos.
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-46.7, -51, Math.toRadians(143+90)))
                .waitSeconds(2)

                .strafeToLinearHeading(new Vector2d(-25, -25), Math.toRadians(225))
                .strafeToLinearHeading(new Vector2d(-11, -21), Math.toRadians(270)) // prepare set 2
                .strafeToLinearHeading(new Vector2d(-12, -55), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(-3,-46),Math.toRadians(180))
                .strafeTo(new Vector2d(0,-55))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d( -25, -25), Math.toRadians(225))
                .strafeToLinearHeading(new Vector2d(13.2, -21), Math.toRadians(270), //prepare set 3
                        new TranslationalVelConstraint(80))
                .strafeToLinearHeading(new Vector2d(13.2, -63), Math.toRadians(270), //pickup set 3
                        new TranslationalVelConstraint(80))
                .strafeToLinearHeading(new Vector2d(13.2, -40), Math.toRadians(270), //draw back set 3
                        new TranslationalVelConstraint(85))
                .strafeToLinearHeading(new Vector2d(-25, -25), Math.toRadians(225))
                .strafeToLinearHeading(new Vector2d(35.5, -21), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(36, -64), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(36, -40), Math.toRadians(270)) //draw back set 3
                .strafeToLinearHeading(new Vector2d(-44, -25), Math.toRadians(245))




                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();

    }
}