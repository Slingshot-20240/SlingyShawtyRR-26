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

                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(203))

                //SAME AS CLOSE SIDE 4th SET VALUES!!!!!!!!!!!!!!!!!!!!!!!!!
                .strafeToLinearHeading(new Vector2d(36, -22), Math.toRadians(270)) // prepareSet1Pose
                .strafeToLinearHeading(new Vector2d(36, -61.5), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(203))


//                //SAME AS CLOSE SIDE 3rd SET VALUES!!!!!!!!!!!!!!!!!!!!!!!!!
//                .strafeToLinearHeading(new Vector2d(12.4,-22),Math.toRadians(270))
//                .strafeToLinearHeading(new Vector2d(12.4,-61.5), Math.toRadians(270))
//                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(203))

                //set 3?
                .strafeToLinearHeading(new Vector2d(40, -61.5), Math.toRadians(0))
                .strafeToLinearHeading(new Vector2d(61.5, -61.5), Math.toRadians(0),
                        new TranslationalVelConstraint(90))

                //park
                .strafeTo(new Vector2d(38, -32))


                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();

    }
}