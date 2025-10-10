package org.firstinspires.ftc.teamcode.subsystems.shooter;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Shooter {
    private final DcMotorEx outtakeL;
    private final DcMotorEx outtakeR;
    private final Servo variableHoodL;
    private final Servo variableHoodR;

    public Shooter(HardwareMap hardwareMap) {
        outtakeL = hardwareMap.get(DcMotorEx.class, "outtakeL");
        outtakeR = hardwareMap.get(DcMotorEx.class, "outtakeL");
        outtakeL.setDirection(DcMotorSimple.Direction.FORWARD);
        outtakeR.setDirection(DcMotorSimple.Direction.FORWARD);

        outtakeL.setVelocityPIDFCoefficients(0,0,0,0);
        outtakeR.setVelocityPIDFCoefficients(0,0,0,0);

        variableHoodR = hardwareMap.get(Servo.class, "variableHoodR");
        variableHoodL = hardwareMap.get(Servo.class, "variableHoodL");

        outtakeL.setDirection(DcMotorSimple.Direction.REVERSE);
        variableHoodL.setDirection(Servo.Direction.REVERSE);
    }

    public enum outtakeVels {
        PID_SHOOT(shootVel),
        // 5.059
        HARDCODED_SHOOT_TRIANGLE(convertVelToRPM(Math.sqrt(H * g + g * Math.sqrt(Math.pow(front_dist, 2) + Math.pow(H, 2))))),
        // 5.954
        HARDCODED_SHOOT_BACK(convertVelToRPM(Math.sqrt(H * g + g * Math.sqrt(Math.pow(back_dist, 2) + Math.pow(H, 2))))),
        IDLE(0);

        private final double outtake_vels;

        outtakeVels(double pos) {
            this.outtake_vels = pos;
        }
        public double getOuttakeVel() {
            return outtake_vels;
        }
    }

    //-----------------Math-----------------\\
    private static final double launchHeight = 0; // TODO update this with CAD
    private static final double g = 9.81;
    private static final double H = .39 - launchHeight; // y distance, m distance from launch height to a little above hole on goal
    private static double shootVel;
    private static double R; // TODO: update R with April Tag value

    // Hardcoded distances from tip of triangle points to the middle of the goal (meters)
    // TODO: ADJUST FOR ROBOT DIMENSIONS
    static double front_dist = 2.1844;
    static double back_dist = 3.2004;

    public double calculateShooterVel() {
        double convertedVel = 0;
        double power = 0;
        // TODO: update to RPM
        shootVel = Math.sqrt(H * g + g * Math.sqrt(Math.pow(R, 2) + Math.pow(H, 2)));
        // .096 is the diameter in m of the flywheel
        power = convertVelToRPM(shootVel);
        return power;
    }

    public static double convertVelToRPM(double vel) {
        double newVel = (vel * 60) / 2 * .096 * Math.PI; // vel in RPM
        return newVel/6000;
    }

    // HOOD ANGLE CALCULATIONS - TODO: ASK RUPAL
    // ---------------------------------
    private static double hoodAngle;

    public double calculateHoodAngle() {
        hoodAngle = Math.atan(Math.pow(Shooter.getShootVel(), 2)/(g * R)) / 2 * Math.PI;
        return hoodAngle;
    }
    //Added by Ishaan idk if its right
    public static double getShootVel() {
        return shootVel;
    }



//Actions
    //-----------------------------Idle--------------------------------------\\
    public class ShooterIdle implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            outtakeL.setPower(0);
            outtakeR.setPower(0);
            return false;
        }
    }
    public Action idle() {
        return new ShooterIdle();
    }

    //-----------------------------Out--------------------------------------\\
    public class ShooterOut implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            outtakeL.setPower(-1.0);
            outtakeR.setPower(-1.0);
            return false;
        }
    }
    public Action out() {
        return new ShooterOut();
    }


    public void hoodToBackTriPos() {
        variableHoodR.setPosition(Math.atan(Math.pow(Shooter.getShootVel(), 2)/(g * back_dist)) / 2 * Math.PI);
        variableHoodL.setPosition(Math.atan(Math.pow(Shooter.getShootVel(), 2)/(g * back_dist)) / 2 * Math.PI);
    }

    public void hoodToFrontTriPos() {
        variableHoodR.setPosition(Math.atan(Math.pow(Shooter.getShootVel(), 2)/(g * front_dist)) / 2 * Math.PI);
        variableHoodL.setPosition(Math.atan(Math.pow(Shooter.getShootVel(), 2)/(g * front_dist)) / 2 * Math.PI);
    }

    public void shootFromBack() {
        outtakeL.setPower(outtakeVels.HARDCODED_SHOOT_BACK.getOuttakeVel());
        outtakeR.setPower(outtakeVels.HARDCODED_SHOOT_BACK.getOuttakeVel());
    }

    public void shootFromFront() {
        outtakeL.setPower(outtakeVels.HARDCODED_SHOOT_TRIANGLE.getOuttakeVel());
        outtakeR.setPower(outtakeVels.HARDCODED_SHOOT_TRIANGLE.getOuttakeVel());
    }
}

