package org.firstinspires.ftc.teamcode.teleop.fsm;


import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Roadrunner.Localizer;
import org.firstinspires.ftc.teamcode.subsystems.robot.Robot;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.firstinspires.ftc.teamcode.subsystems.drivetrain.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;
import org.firstinspires.ftc.teamcode.subsystems.turret.Turret;

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

        drive.update();
        pose = drive.getPose();
        //TODO - Get robot pos from localization

        switch (state) {
            case BASE_STATE:
                // if hardcoded control, set hood to back shooting position
                if (type.equals(ControlType.HARDCODED_CONTROL)) {
                    shooter.hoodToBackTriPos();
                }
                // shooter off :)
                shooter.setShooterPower(0);

                // Going to try transfer always on, may need to add some delays
                transfer.on();

                // Intake button toggle, intake on/off
                if (gamepad.intake.value()) {
                    intake.in();
                } else if (!gamepad.intake.value())
                    intake.idle();

                if (gamepad.pidShoot.value() || gamepad.shootTriangle.value() || gamepad.shootBack.value()) {
                    state = FSMStates.SHOOTING;
                }

                if (gamepad.park.value()) {
                    state = FSMStates.PARK;
                }

                break;

            case SHOOTING:
                // TODO: FIX THIS ASK BOOP - BEE
                //I (Ishaan) Commented line below cuz i got NO clue how to suppress errors.
                //Actually i do, we just have to add localization
                //turret.setTurretPos(turret.calcTurretVal(pose.getX(), pose.getY(), pose.getX(), pose.getY(), pose.getHeading()), 1);
                // turn transfer off while shooting until back to base state
                transfer.off();
                // Hardcoded control AND we're at the back shooting zone
                if (type == ControlType.HARDCODED_CONTROL && gamepad.shootBack.value()) {
                    shooter.hoodToBackTriPos();
                    shooter.shootFromBack();
                }
                // Hardcoded control AND we're at the tip of the triangle of the front shooting zone
                else if (type == ControlType.HARDCODED_CONTROL && gamepad.shootTriangle.value()) {
                    shooter.hoodToFrontTriPos();
                    shooter.shootFromFront();
                }
                // PID control that adjusts depending on our distance - TO BE IMPLEMENTED
                else if (type == ControlType.PID_CONTROL && gamepad.pidShoot.value()) {
                   shooter.setShooterPower(shooter.calculateShooterVel());
                   shooter.setHoodAngle(shooter.calculateHoodAngle());
                }
                // Return to base state if shooting is false
                // TODO: this may not work
                if (gamepad.pidShoot.changed() || gamepad.shootTriangle.changed() || gamepad.shootBack.changed()) {
                    state = FSMStates.BASE_STATE;
                    gamepad.resetMultipleControls(gamepad.pidShoot, gamepad.shootBack, gamepad.shootTriangle);
                }
                break;

            case PARK:
                // TODO Make if we do park
                // park.extend()

                if (!gamepad.park.value()) {
                    state = FSMStates.BASE_STATE;
                    // park.retract();
                }

        }
    }

    public enum FSMStates {
        BASE_STATE,
        SHOOTING,
        PARK,
        TRANSFER
    }

    public enum ControlType {
        HARDCODED_CONTROL,
        PID_CONTROL
    }

}
