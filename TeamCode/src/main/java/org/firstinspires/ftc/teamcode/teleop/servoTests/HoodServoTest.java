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

    // HOOD SERVO RANGE
    // .65 is all the way down
    // .05 is all the way up

    public static double servoPos = 0;
    public static double power = 0;
    GamepadMapping controls;
    private Servo variableHood;
    private Robot robot;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, controls);
        variableHood = hardwareMap.get(Servo.class, "variableHood");
    }

    @Override
    public void loop() {
        controls.update();
        robot.shooter.setShooterVelocity(-power);
        variableHood.setPosition(servoPos);

        // right bumper
        if (controls.transfer.value()) {
            robot.transfer.transferOn();
        }
        // left bumper
        if (controls.intake.value()) {
            robot.intake.intakeOn();
        }
    }
}
