package org.firstinspires.ftc.teamcode.teleop.servoTests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.robot.Robot;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@Config
@TeleOp (group = "servo tests")
public class HoodServoTest extends OpMode {
    //Robot robot;
    public static double servoPos = 0;
    GamepadMapping controls;
    private Servo variableHoodL;
    private Servo variableHoodR;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        //robot = new Robot(hardwareMap, controls);
        variableHoodL = hardwareMap.get(Servo.class, "variableHoodL");
        variableHoodR = hardwareMap.get(Servo.class, "variableHoodR");
    }

    @Override
    public void loop() {
        variableHoodL.setPosition(servoPos);
        variableHoodR.setPosition(servoPos);
    }
}
