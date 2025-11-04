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



        // Declare out second bot
        RoadRunnerBotEntity blueBack = new DefaultBotBuilder(meepMeep)
                // We set this bot to be red
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        blueBack.runAction(blueBack.getDrive().actionBuilder(new Pose2d(61.5, -14, Math.toRadians(180)))
                .waitSeconds(2)
                //score preloads
                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(210))
                .waitSeconds(7)

                //grab set 1
                .splineTo(new Vector2d(36,-45),Math.toRadians(270))
                .splineTo(new Vector2d(36,-62),Math.toRadians(270))
                //score set 1
                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(210))
                .waitSeconds(4.5)

                //grab set 2
                .strafeToLinearHeading(new Vector2d(12,-22),Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(12.3,-62),Math.toRadians(270))

                //score set 1
                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(210))
                .waitSeconds(4.5)

                //park
                .strafeToLinearHeading(new Vector2d(35, -20), Math.toRadians(180))

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