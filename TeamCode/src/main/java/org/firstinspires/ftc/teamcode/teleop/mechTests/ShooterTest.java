package org.firstinspires.ftc.teamcode.teleop.mechTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@TeleOp(group = "shooter mech test")
public class ShooterTest extends OpMode {
    Robot robot;
    GamepadMapping controls;

    public static boolean backShoot = false;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, controls);
    }

    @Override
    public void loop() {
        controls.update();

        if (backShoot) {
            if(controls.shootBack.value()) {
                robot.shooter.shootFromBack();
            } else {
                robot.transfer.off();
            }
        }

        if (!backShoot) {
            if(controls.shootTriangle.value()) {
                robot.shooter.shootFromFront();
            } else {
                robot.transfer.off();
            }
        }
    }
}
