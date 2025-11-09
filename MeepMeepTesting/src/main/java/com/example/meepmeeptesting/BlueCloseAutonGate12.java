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
                //preload
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(-25, -25), Math.toRadians(225))
                .waitSeconds(4)

                // Set 1
                //grab set 1
                .strafeToLinearHeading(new Vector2d(-11, -22), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(-12, -56), Math.toRadians(270))

                //gate
                .strafeToLinearHeading(new Vector2d(3, -52), Math.toRadians(180),
                        new TranslationalVelConstraint(70))

                //score set 1
                .strafeToLinearHeading(new Vector2d(-25, -25), Math.toRadians(225))
                .waitSeconds(3)

                // Set 2
                //grab set 2
                .strafeToLinearHeading(new Vector2d(12.4, -22), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(13, -60), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(13, -49), Math.toRadians(270))

                //score set 2
                .strafeToLinearHeading(new Vector2d(-25, -25), Math.toRadians(225))
                .waitSeconds(3)


                // Set 3
                //go to set 3
                .strafeToLinearHeading(new Vector2d(36, -24), Math.toRadians(270))


                //Pickup and Score with Spline
//                .strafeToLinearHeading(new Vector2d(35.6, -43), Math.toRadians(270))
//                .splineToLinearHeading(new Pose2d(-44,-24, Math.toRadians(245)), Math.toRadians(167))

                //Pickup and Score with Strafe
                .strafeToLinearHeading(new Vector2d(35.6, -60), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(-44, -25), Math.toRadians(245))




                //score set 3
//                .strafeToLinearHeading(new Vector2d(-44, -24), Math.toRadians(245),
//                        new TranslationalVelConstraint(40))

                .waitSeconds(3)


                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();

    }
}