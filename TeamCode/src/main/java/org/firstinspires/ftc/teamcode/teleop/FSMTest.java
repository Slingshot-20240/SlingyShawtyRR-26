package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.teleop.fsm.FSM;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

import dev.nextftc.ftc.NextFTCOpMode;

@TeleOp
public class FSMTest extends NextFTCOpMode {
    private Robot robot;
    private FSM fsm;
    private GamepadMapping gamepad;

    @Override
    public void onInit() {
        gamepad = new GamepadMapping(gamepad1,gamepad2);
        fsm = new FSM(hardwareMap, gamepad);
        robot = fsm.robot;
    }

    @Override
    public void onUpdate() {
        fsm.update();
    }
}
