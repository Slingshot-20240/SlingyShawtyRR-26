package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepMaster {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        // Declare our first bot
        RoadRunnerBotEntity blueFront = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        blueFront.runAction(blueFront.getDrive().actionBuilder(new Pose2d(-55, -45, Math.toRadians(180-37)))
                        .strafeToLinearHeading(new Vector2d(-25, -25), Math.toRadians(225))
                        .waitSeconds(4)

                        // Set 1
                        //grab set 1
                        .strafeToLinearHeading(new Vector2d(-11, -22), Math.toRadians(270))
                        .strafeToLinearHeading(new Vector2d(-12, -53), Math.toRadians(270))

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
                        //grab balls
                        .strafeToLinearHeading(new Vector2d(36, -24), Math.toRadians(270))
                        .strafeToLinearHeading(new Vector2d(35.6, -43), Math.toRadians(270))

                        //score balls
                        .splineToLinearHeading(new Pose2d(-44,-24, Math.toRadians(245)), Math.toRadians(167))



                        //score set 3
//                .strafeToLinearHeading(new Vector2d(-44, -24), Math.toRadians(245),
//                        new TranslationalVelConstraint(40))
                        .waitSeconds(3)
                        .build());



        // Declare out second bot
        RoadRunnerBotEntity blueBack = new DefaultBotBuilder(meepMeep)
                // We set this bot to be red
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        blueBack.runAction(blueBack.getDrive().actionBuilder(new Pose2d(61.5, -14, Math.toRadians(180)))
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
                // Add both of our declared bot entities
                .addEntity(blueFront)
                .addEntity(blueBack)

                .start();
    }
}