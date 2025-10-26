package org.firstinspires.ftc.teamcode.teleop.misc;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.subsystems.robot.Robot;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

public class PracticeFSMOpMode extends OpMode {

    GamepadMapping controls = new GamepadMapping(gamepad1, gamepad2);
    Robot robot = new Robot(hardwareMap, controls);

    @Override
    public void init() {

    }

    @Override
    public void loop() {
        robot.drivetrain.update();

        if(gamepad1.a) {
            robot.shooter.setShooterVelocity(1);
        } else {
            // turn off shooter
        }

        if (gamepad1.b) {
            // intake
        } else {
            // turn off intake
        }

        if (gamepad1.x) {
            // turn on transfer
        } else {
            // turn off transfer
        }
    }
}
