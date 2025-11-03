package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class RedCloseAuton {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14.8)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-55, 45, Math.toRadians(37+180)))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(-24, 24), Math.toRadians(-225))
                .waitSeconds(4)

                // Set 1
                .strafeToLinearHeading(new Vector2d(-11, 22), Math.toRadians(-270))
                .strafeToLinearHeading(new Vector2d(-12, 51.5), Math.toRadians(-270))
                .strafeToLinearHeading(new Vector2d(-24, 24), Math.toRadians(-225))
                .waitSeconds(4)

                // Set 2
                .strafeToLinearHeading(new Vector2d(12, 22), Math.toRadians(-270))
                .strafeToLinearHeading(new Vector2d(12, 53), Math.toRadians(-270))
                .strafeToLinearHeading(new Vector2d(-24, 24), Math.toRadians(-225))
                .waitSeconds(4)

                // Park
                .strafeToLinearHeading(new Vector2d(-48, 24), Math.toRadians(-180))

                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();

    }
}