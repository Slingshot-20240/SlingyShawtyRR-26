package org.firstinspires.ftc.teamcode.teleop.IshaanStuff;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ShooterController {

    private final DcMotorEx outtake1;
    private final DcMotorEx outtake2;
    private final Servo hoodServo;

    private final double goalX = -72.0;
    private final double goalY = 72.0;

    // ======== FLYWHEEL LINEAR MODEL ========
    private static final double A = 5.135695809070678;     // slope
    private static final double B = 738.6403787257103;     // intercept

    private static final double MIN_VELOCITY = 800.0;
    private static final double MAX_VELOCITY = 1800.0;

    private static final double MAX_DELTA_PER_SEC = 600.0;

    private double lastCmdVel = MIN_VELOCITY;
    private long lastTime = System.nanoTime();

    // ======== HOOD LINEAR MODEL ========
    private static final double C = -0.001621080553;
    private static final double D = 0.528120397;

    // Hood cannot go below this
    private static final double HOOD_MIN = 0.175;
    private static final double HOOD_MAX = 0.5;

    public ShooterController(HardwareMap hw) {
        this.outtake1 = hw.get(DcMotorEx.class, "outtake1");
        this.outtake2 = hw.get(DcMotorEx.class, "outtake2");
        this.hoodServo = hw.get(Servo.class, "variableHood");
    }

    public static double distanceToGoal(double x, double y, double gx, double gy) {
        return Math.hypot(gx - x, gy - y);
    }

    public static double computeVelocity(double dist) {
        return A * dist + B;
    }

    public static double computeHood(double dist) {
        return C * dist + D;
    }

    private static double clamp(double v, double lo, double hi) {
        return Math.max(lo, Math.min(hi, v));
    }

    /** MASTER UPDATE: sets both flywheel motors + hood servo */
    public void updateShooter(double robotX, double robotY) {

        double dist = distanceToGoal(robotX, robotY, goalX, goalY);

        // ===== FLYWHEEL VELOCITY =====
        double targetVel = clamp(computeVelocity(dist), MIN_VELOCITY, MAX_VELOCITY);

        // Slew limit
        long now = System.nanoTime();
        double dt = Math.max(1e-6, (now - lastTime) / 1e9);
        double maxChange = MAX_DELTA_PER_SEC * dt;

        double delta = targetVel - lastCmdVel;
        if (Math.abs(delta) > maxChange) {
            targetVel = lastCmdVel + Math.signum(delta) * maxChange;
        }

        // Set to *both* motors
        outtake1.setVelocity(targetVel);
        outtake2.setVelocity(targetVel);

        lastCmdVel = targetVel;
        lastTime = now;

        // ===== HOOD POSITION =====
        double hoodPos = computeHood(dist);
        hoodPos = clamp(hoodPos, HOOD_MIN, HOOD_MAX);

        hoodServo.setPosition(hoodPos);
    }

    // Optional getters for debugging
    public double getLastVelocity() { return lastCmdVel; }

    public double predictVelocity(double x, double y) {
        return computeVelocity(distanceToGoal(x, y, goalX, goalY));
    }

    public double predictHood(double x, double y) {
        return computeHood(distanceToGoal(x, y, goalX, goalY));
    }
}
