package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.firstinspires.ftc.teamcode.subsystems.robot.Robot;
import org.firstinspires.ftc.teamcode.teleop.fsm.FSM;

@TeleOp
public class ASlingTele extends OpMode {
    private GamepadMapping controls;
    private FSM fsm;
    private Robot robot;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, controls);
        fsm = new FSM(hardwareMap, controls);
    }

    @Override
    public void start() {
        // run once when we start
        robot.hardwareSoftReset();
    }

    @Override
    public void loop() {
        fsm.update();
    }
}
