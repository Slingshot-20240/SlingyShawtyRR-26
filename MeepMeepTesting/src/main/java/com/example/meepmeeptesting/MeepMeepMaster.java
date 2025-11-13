package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
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

                .strafeToLinearHeading(new Vector2d(-25, -25), Math.toRadians(225))
                .strafeToLinearHeading(new Vector2d(-11, -21), Math.toRadians(270)) // prepare set 2
                .strafeToLinearHeading(new Vector2d(-12, -55), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(-3,-46),Math.toRadians(180))
                .strafeTo(new Vector2d(0,-55))
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d( -25, -25), Math.toRadians(225))
                .strafeToLinearHeading(new Vector2d(13, -21), Math.toRadians(270), //prepare set 3
                        new TranslationalVelConstraint(80))
                .strafeToLinearHeading(new Vector2d(13, -63), Math.toRadians(270), //pickup set 3
                        new TranslationalVelConstraint(80))
                .strafeToLinearHeading(new Vector2d(13, -40), Math.toRadians(270), //draw back set 3
                        new TranslationalVelConstraint(85))
                .strafeToLinearHeading(new Vector2d(-25, -25), Math.toRadians(225))
                .strafeToLinearHeading(new Vector2d(35.5, -21), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(36, -64), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(36, -40), Math.toRadians(270)) //draw back set 3
                .strafeToLinearHeading(new Vector2d(-44, -25), Math.toRadians(245))
                .waitSeconds(2)

                .build());



        // Declare out second bot
        RoadRunnerBotEntity blueBack = new DefaultBotBuilder(meepMeep)
                // We set this bot to be red
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        blueBack.runAction(blueBack.getDrive().actionBuilder(new Pose2d(61.5, -14, Math.toRadians(180)))
                .waitSeconds(2)

                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(203))

                //SAME AS CLOSE SIDE 4th SET VALUES!!!!!!!!!!!!!!!!!!!!!!!!!
                .strafeToLinearHeading(new Vector2d(36, -22), Math.toRadians(270)) // prepareSet1Pose
                .strafeToLinearHeading(new Vector2d(36, -61.5), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(203))


                //SAME AS CLOSE SIDE 3rd SET VALUES!!!!!!!!!!!!!!!!!!!!!!!!!
                .strafeToLinearHeading(new Vector2d(13,-22),Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(13,-61.5), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(55, -12), Math.toRadians(203))

                //set 4?
                .strafeToLinearHeading(new Vector2d(40, -61.5), Math.toRadians(0))
                .strafeToLinearHeading(new Vector2d(61.5, -61.5), Math.toRadians(0),
                        new TranslationalVelConstraint(90))

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