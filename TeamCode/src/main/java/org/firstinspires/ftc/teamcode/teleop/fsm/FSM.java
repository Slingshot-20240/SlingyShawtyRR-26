package org.firstinspires.ftc.teamcode.teleop.fsm;


import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Roadrunner.Localizer;
import org.firstinspires.ftc.teamcode.subsystems.robot.Robot;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.firstinspires.ftc.teamcode.subsystems.drivetrain.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

public class FSM {
    // GENERAL ROBOT STATES + CLASSES
    public Robot robot;
    public FSMStates state = FSMStates.BASE_STATE;
    public ControlType type = ControlType.HARDCODED_CONTROL;
    private final GamepadMapping gamepad;
    private Localizer drive;
    private Pose2d pose;

    // SUBSYSTEMS
    private final Intake intake;
    //private Turret turret;
    private final Transfer transfer;
    private final Shooter shooter;
    private final Drivetrain drivetrain;


    public FSM(HardwareMap hardwareMap, GamepadMapping gamepad) {
        robot = new Robot(hardwareMap, gamepad);
        this.gamepad = robot.controls;

        intake = robot.intake;
        //turret = robot.turret;
        transfer = robot.transfer;
        shooter = robot.shooter;

        drivetrain = robot.drivetrain;
    }

    public void update() {
        // Updates driver controls here as well
        drivetrain.update();
        // Updates all other controls
        gamepad.update();

        // drive.update();
        // pose = drive.getPose();
        // TODO - Get robot pos from localization

        switch (state) {
            case BASE_STATE:
                shooter.shootFromFront();

                intake.intakeOn();

                if (gamepad.transfer.value()) {
                    transfer.transferOn();
                } else {
                    // Hotdog the ball!
                    transfer.backReverseFrontForward();
                }

                if (gamepad.outtake.locked()) {
                    state = FSMStates.OUTTAKING;
                }

//                NOT FOR LM1
//                if hardcoded control, set hood to back shooting position
//                if (type.equals(ControlType.HARDCODED_CONTROL)) {
//                    shooter.hoodToBackTriPos();
//                }
//                if (gamepad.intake.value()) {
//                    intake.intakeOn();
//                    transfer.backReverseFrontForward();
//
//                } else if (!gamepad.intake.value())
//                    intake.intakeOff();
//                    transfer.backReverseFrontForward();

//                if (gamepad.pidShoot.value() || gamepad.shootFront.value() || gamepad.shootBack.value()) {
//                    state = FSMStates.SHOOTING;
//                }

                break;
            case OUTTAKING:
                intake.intakeReverse();
                if (!gamepad.outtake.locked()) {
                    state = FSMStates.BASE_STATE;
                    gamepad.resetMultipleControls(gamepad.transfer);
                }
                break;

//            case SHOOTING:
                // intake on
                // TODO: FIX THIS FOR TURRET
                //turret.setTurretPos(turret.calcTurretVal(pose.getX(), pose.getY(), pose.getX(), pose.getY(), pose.getHeading()), 1);

//                if (type == ControlType.HARDCODED_CONTROL && gamepad.shootBack.value()) {
//                    //shooter.hoodToBackTriPos();
//                    shooter.shootFromBack();
//
//                    if (gamepad.transfer.value()) {
//                        robot.transfer.transferOn();
//                    } else {
//                        robot.transfer.backReverseFrontForward();
//                    }
//                }

                // Hardcoded control AND we're at the tip of the triangle of the front shooting zone
//                else if (type == ControlType.HARDCODED_CONTROL && gamepad.shootFront.value()) {
//                    //shooter.hoodToFrontTriPos();
//                    shooter.shootFromFront();
//
//                    if (gamepad.transfer.value()) {
//                        robot.transfer.transferOn();
//                    } else {
//                        robot.transfer.backReverseFrontForward();
//                    }
//                }

                // PID control that adjusts depending on our distance - TO BE IMPLEMENTED
//                else if (type == ControlType.PID_CONTROL && gamepad.pidShoot.value()) {
//                   shooter.setShooterVelocity(shooter.calculateShooterVel());
//                   shooter.setHoodAngle(shooter.calculateHoodAngle());
//                }

//                if (gamepad.pidShoot.changed() || gamepad.shootFront.changed() || gamepad.shootBack.changed()) {
//                    state = FSMStates.BASE_STATE;
//                    transfer.transferOff();
//                    gamepad.resetMultipleControls(gamepad.pidShoot, gamepad.shootBack, gamepad.shootFront, gamepad.intake, gamepad.transfer);
//                }
//                break;
        }
    }

    public enum FSMStates {
        BASE_STATE,
        SHOOTING,
        PARK,
        OUTTAKING
    }

    public enum ControlType {
        HARDCODED_CONTROL,
        PID_CONTROL
    }

}
