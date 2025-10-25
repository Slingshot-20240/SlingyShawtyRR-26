package org.firstinspires.ftc.teamcode.subsystems.shooter;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.base_templates.Servo_Template;

public class Shooter {
    public final DcMotorEx outtake;

    // HOOD SERVO RANGE
    // .65 is all the way down
    // .05 is all the way up
    public final Servo variableHood;

    public Shooter(HardwareMap hardwareMap) {
        outtake = hardwareMap.get(DcMotorEx.class, "outtake");
        outtake.setVelocityPIDFCoefficients(0,0,0,0);
        outtake.setDirection(DcMotorSimple.Direction.REVERSE);

        variableHood = hardwareMap.get(Servo.class, "variableHood");

        variableHood.setDirection(Servo.Direction.REVERSE);
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

    public static double getShootVel() {
        return shootVel;
    }



//Actions
    //-----------------------------Idle--------------------------------------\\
    public class ShooterIdle implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            outtake.setPower(0);
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
            outtake.setPower(-1.0);
            return false;
        }
    }
    public Action out() {
        return new ShooterOut();
    }
//-------------------------------------------------------------------------------




    public void setShooterPower(double power) {
        outtake.setPower(power);
    }

    public void setHoodAngle(double angle) {
        variableHood.setPosition(angle);
    }

    public void hoodToBackTriPos() {
        variableHood.setPosition(Math.atan(Math.pow(Shooter.getShootVel(), 2)/(g * back_dist)) / 2 * Math.PI);
    }

    public void hoodToFrontTriPos() {
        variableHood.setPosition(Math.atan(Math.pow(Shooter.getShootVel(), 2)/(g * front_dist)) / 2 * Math.PI);
    }

    public void shootFromBack() {
        outtake.setPower(outtakeVels.HARDCODED_SHOOT_BACK.getOuttakeVel());
    }

    public void shootFromFront() {
        outtake.setPower(outtakeVels.HARDCODED_SHOOT_TRIANGLE.getOuttakeVel());
    }

}



