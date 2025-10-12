package org.firstinspires.ftc.teamcode.teleop.mechTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.robot.Robot;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@TeleOp(group = "intake mech tests")
public class IntakeTest extends OpMode {
    Robot robot;
    GamepadMapping controls;
    public static double power = 0;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, controls);
    }

    @Override
    public void loop() {
        robot.intake.intake.setPower(power);
    }
}
