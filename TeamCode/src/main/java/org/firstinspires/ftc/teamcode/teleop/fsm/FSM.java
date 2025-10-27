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

    // OTHER
    public final ElapsedTime loopTime;
    public double startTime;
    private int countBalls = 0;


    public FSM(HardwareMap hardwareMap, GamepadMapping gamepad) {
        robot = new Robot(hardwareMap, gamepad);
        this.gamepad = robot.controls;

        intake = robot.intake;
        //turret = robot.turret;
        transfer = robot.transfer;
        shooter = robot.shooter;

        drivetrain = robot.drivetrain;

        loopTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        startTime = loopTime.milliseconds();
    }

    public void update() {
        // Updates driver controls here as well
        drivetrain.update();
        // Updates all other controls
        gamepad.update();

//        drive.update();
//        pose = drive.getPose();
        //TODO - Get robot pos from localization

        switch (state) {
            case BASE_STATE:
                // if hardcoded control, set hood to back shooting position
                if (type.equals(ControlType.HARDCODED_CONTROL)) {
                    shooter.hoodToBackTriPos();
                }

                if (gamepad.outtake.locked()) {
                    state = FSMStates.OUTTAKING;
                }

                shooter.setShooterVelocity(0);

                // TRANSFER ALT
                // always have transfer on but back running backwards, should keep ball in place
                transfer.backReverseFrontForward();

                // Going to try transfer always on, may need to add some delays
                // transfer.transferOn();

                // Intake button toggle, intake on/off
                if (gamepad.intake.value()) {
                    intake.intakeOn();
                    transfer.backReverseFrontForward();

                } else if (!gamepad.intake.value())
                    intake.intakeOff();
                    transfer.backReverseFrontForward();


                if (gamepad.pidShoot.value() || gamepad.shootFront.value() || gamepad.shootBack.value()) {
                    state = FSMStates.SHOOTING;
                }

                if (gamepad.park.value()) {
                    state = FSMStates.PARK;
                }

                break;

//            case TRANSFER_FIRST:
//                // counter, everytime we shoot it resets and that first one is the one we do this
//                // manual override
//                if (loopTime.milliseconds() - startTime <= 1700) {
//                    transfer.transferOn();
//                } else {
//                    transfer.transferOff();
//                    state = FSMStates.BASE_STATE;
//                    gamepad.resetMultipleControls(gamepad.intake);
//                    break;
//                }
//                break;
            case OUTTAKING:
                intake.intakeReverse();
                if (!gamepad.outtake.locked()) {
                    state = FSMStates.BASE_STATE;
                    gamepad.resetMultipleControls(gamepad.intake, gamepad.shootBack, gamepad.shootFront, gamepad.transfer);
                }
                break;

            case SHOOTING:
                intake.intakeOn();
                // TODO: FIX THIS ASK BOOP - BEE
                //I (Ishaan) Commented line below cuz i got NO clue how to suppress errors.
                //Actually i do, we just have to add localization
                //turret.setTurretPos(turret.calcTurretVal(pose.getX(), pose.getY(), pose.getX(), pose.getY(), pose.getHeading()), 1);
                // turn transfer off while shooting until back to base state
                // Hardcoded control AND we're at the back shooting zone
                if (type == ControlType.HARDCODED_CONTROL && gamepad.shootBack.value()) {
                    shooter.hoodToBackTriPos();
                    shooter.shootFromBack();

                    if (gamepad.transfer.value() && robot.shooter.outtake.getVelocity() <= Shooter.outtakeVels.HARDCODED_SHOOT_BACK.getOuttakeVel()) {
                        robot.transfer.transferOn();
                    } else {
                        //robot.transfer.transferOff();
                        //ISHAAN ADDED HOTDOG
                        robot.transfer.backReverseFrontForward();
                    }
                }
                // Hardcoded control AND we're at the tip of the triangle of the front shooting zone
                else if (type == ControlType.HARDCODED_CONTROL && gamepad.shootFront.value()) {
                    shooter.hoodToFrontTriPos();
                    shooter.shootFromFront();

                    if (gamepad.transfer.value() && robot.shooter.outtake.getVelocity() <= Shooter.outtakeVels.HARDCODED_SHOOT_FRONT.getOuttakeVel()) {
                        robot.transfer.transferOn();
                    } else {
                        robot.transfer.backReverseFrontForward();
                    }
                }
                // PID control that adjusts depending on our distance - TO BE IMPLEMENTED
                else if (type == ControlType.PID_CONTROL && gamepad.pidShoot.value()) {
                   shooter.setShooterVelocity(shooter.calculateShooterVel());
                   shooter.setHoodAngle(shooter.calculateHoodAngle());
                }
                // Return to base state if shooting is false
                // TODO: this may not work
                if (gamepad.pidShoot.changed() || gamepad.shootFront.changed() || gamepad.shootBack.changed()) {
                    countBalls = 0;
                    state = FSMStates.BASE_STATE;
                    transfer.transferOff();
                    gamepad.resetMultipleControls(gamepad.pidShoot, gamepad.shootBack, gamepad.shootFront, gamepad.intake, gamepad.transfer);
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
        OUTTAKING
    }

    public enum ControlType {
        HARDCODED_CONTROL,
        PID_CONTROL
    }

}
