package org.firstinspires.ftc.teamcode.teleop.servoTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.robot.Robot;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@TeleOp(group = "transfer servo tests")
public class TransferServoTest extends OpMode {
    Robot robot;
    public static double servoPos = 0;
    GamepadMapping controls;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, controls);
    }

    @Override
    public void loop() {
        robot.transfer.backTransfer.setPower(-servoPos);
        robot.transfer.frontTransfer.setPower(servoPos);
    }
}
