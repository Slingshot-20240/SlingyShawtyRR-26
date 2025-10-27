package org.firstinspires.ftc.teamcode.misc.gamepad;

import com.qualcomm.robotcore.hardware.Gamepad;

public class GamepadMapping {
    // GAMEPADS
    private final Gamepad gamepad1;
    private final Gamepad gamepad2;

    // DRIVETRAIN
    // --------------
    public static double drive = 0.0;
    public static double strafe = 0.0;
    public static double turn = 0.0;

    // INTAKE
    public Toggle intake;

    // SHOOTER
    public Toggle pidShoot;
    public Toggle shootBack;
    public Toggle shootFront;

    // PARK
    public Toggle park;

    // MISC
    public Toggle failSafeReset;
    public Toggle transferCounter;
    public Toggle transfer;
    public Toggle outtake;

    public GamepadMapping(Gamepad gamepad1, Gamepad gamepad2) {
        // GAMEPADS
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

        // INTAKE
        intake = new Toggle(false);

        // SHOOTER
        shootBack = new Toggle(false);
        shootFront = new Toggle(false);
        pidShoot = new Toggle(false);

        // PARK
        // TODO: Match to a button
        park = new Toggle(false);

        // MISC
        failSafeReset = new Toggle(false);
        transferCounter = new Toggle(false);
        transfer = new Toggle(false);
        outtake = new Toggle(false);
    }

    public void joystickUpdate() {
        drive = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;
    }

    public void update() {
        joystickUpdate();
        // INTAKE
        intake.update(gamepad1.left_bumper);

        // SHOOTER
        shootBack.update(gamepad2.left_trigger >= 0.5);
        shootFront.update(gamepad2.left_bumper);
        //pidShoot.update(gamepad2.right_bumper);

        failSafeReset.update(gamepad1.dpad_down);
        // transferCounter.update(gamepad2.right_bumper);

        // TEMP TRANSFER
        transfer.update(gamepad2.dpad_up);

        outtake.update(gamepad1.y);
    }

    public void resetMultipleControls(Toggle... toggles) {
        for (Toggle toggle : toggles) {
            toggle.set(false);
        }
    }
}

