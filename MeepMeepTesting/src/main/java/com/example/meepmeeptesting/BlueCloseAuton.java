package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class BlueCloseAuton {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14.8)
                .build();

        //90 - 53 = 47. 47 + 180 will get you the red starting pos.
        //53 + 90 will get you the blue starting pos.
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-55, -45, Math.toRadians(180-37)))
                .waitSeconds(2)
                //preload
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .waitSeconds(6)

            // Set 1
                //grab set 1
                .strafeToLinearHeading(new Vector2d(-11, -22), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(-12, -53), Math.toRadians(270))

                //score set 1
                .strafeToLinearHeading(new Vector2d(-24, -24), Math.toRadians(225))
                .waitSeconds(4.5)

            // Set 2
                //grab set 2
                .strafeToLinearHeading(new Vector2d(12, -22), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(12.3, -60), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(12.3, -49), Math.toRadians(270))

                //score set 2
                .strafeToLinearHeading(new Vector2d(-44, -24), Math.toRadians(245))


                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();

    }
}