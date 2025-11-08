package org.firstinspires.ftc.teamcode.subsystems.shooter;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Shooter {
    public final DcMotorEx outtake1;
    public final DcMotorEx outtake2;
    public final Servo variableHood;

    public Shooter(HardwareMap hardwareMap) {
        outtake1 = hardwareMap.get(DcMotorEx.class, "outtake1");
        outtake2 = hardwareMap.get(DcMotorEx.class, "outtake2");
        outtake1.setVelocityPIDFCoefficients(600,0,0,40);
        outtake2.setVelocityPIDFCoefficients(600,0,0,40);
        outtake2.setDirection(DcMotorSimple.Direction.REVERSE);

        variableHood = hardwareMap.get(Servo.class, "variableHood");
    }

    public enum outtakeVels {
        PID_SHOOT(shootVel),
        // 5.059
        HARDCODED_SHOOT_FRONT(-1120),
        // 5.954
        HARDCODED_SHOOT_BACK(-1400),
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
    private static final double launchHeight = .280; // meters
    private static final double g = 9.81;
    private static final double H = .39 - launchHeight; // y distance, m distance from launch height to a little above hole on goal
    private static double shootVel;
    private static double R; // TODO: update R with April Tag value

    // Hardcoded distances from tip of triangle points to the middle of the goal (meters)
    static double front_dist = 2.1844 ;
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
        double newVel = (vel * 60) / .086 * Math.PI; // vel in RPM
        return -newVel/6000;
    }

    // HOOD ANGLE CALCULATIONS - TODO: ASK RUPAL
    // ---------------------------------
    private static double hoodAngle;

    public double calculateHoodAngle() {
        hoodAngle = Math.atan(Math.pow(Shooter.getShootVel(), 2)/(g * R)) / 2 * Math.PI;
        return hoodAngle;
    }

    public static double getShootVel() {
        return shootVel;
    }



//Actions
    //-----------------------------Idle--------------------------------------\\
    public class ShooterIdle implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            outtake1.setVelocityPIDFCoefficients(600,0,0,40);
            outtake2.setVelocityPIDFCoefficients(600,0,0,40);
            outtake1.setVelocity(0);
            outtake2.setVelocity(0);
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
            outtake1.setVelocityPIDFCoefficients(600,0,0,40);
            outtake2.setVelocityPIDFCoefficients(600,0,0,40);
            outtake1.setVelocity(-1020);
            outtake2.setVelocity(-1020);
            return false;
        }
    }
    public Action out() {
        return new ShooterOut();
    }
//-------------------------------------------------------------------------------




    public void setShooterVelocity(double velo) {
        outtake1.setVelocity(velo);
        outtake2.setVelocity(velo);
    }

    public void setHoodAngle(double angle) {
        variableHood.setPosition(angle);
    }
    // fully down is .6
    // fully up is .1
    public void hoodToBack() {
        variableHood.setPosition(.1);
    }

    public void hoodToFront() {
        variableHood.setPosition(.5);
    }

    public void shootFromBack() {
        outtake1.setVelocity(outtakeVels.HARDCODED_SHOOT_BACK.getOuttakeVel());
        outtake2.setVelocity(outtakeVels.HARDCODED_SHOOT_BACK.getOuttakeVel());
    }

    public void shootFromFront() {
        outtake1.setVelocity(outtakeVels.HARDCODED_SHOOT_FRONT.getOuttakeVel());
        outtake2.setVelocity(outtakeVels.HARDCODED_SHOOT_FRONT.getOuttakeVel());
    }

}



