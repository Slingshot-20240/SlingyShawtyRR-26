package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class BlueFarAuton {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14.8)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(61.5, -14, Math.toRadians(180)))
                .waitSeconds(2)
                //score preloads
                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(201))
                .waitSeconds(4)

                //grab set 1
                .splineTo(new Vector2d(36,-45),Math.toRadians(270))
                .splineTo(new Vector2d(36,-62),Math.toRadians(270))
                //score set 1
                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(201))
                .waitSeconds(3)

                //grab set 2
                .strafeToLinearHeading(new Vector2d(12.8,-22),Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(13,-62),Math.toRadians(270))

                //score set 2
                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(201))
                .waitSeconds(3)

                // Set 3
                //grab set 3
                .strafeToLinearHeading(new Vector2d(-11, -22), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(-12, -53), Math.toRadians(270))

                 //score set 3
                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(201))
                .waitSeconds(3)

                //park
                .strafeToLinearHeading(new Vector2d(20, -20), Math.toRadians(180))

                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();

    }
}