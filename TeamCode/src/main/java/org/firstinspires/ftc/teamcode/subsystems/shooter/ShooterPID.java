package org.firstinspires.ftc.teamcode.subsystems.shooter;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


import org.firstinspires.ftc.robotcore.external.Telemetry;


@Config
@TeleOp(name = "ShooterPID", group = "Testing")
public class ShooterPID extends LinearOpMode {


    DcMotorEx flywheel;
    public static double p = 0.2, i = 2.0, d = 0.002, f = 0.0;
    public static int targetVel = 500;
    private Telemetry dashboardTelemetry;
    @Override
    public void runOpMode() {
        flywheel.setDirection(DcMotorSimple.Direction.REVERSE);
        dashboardTelemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        flywheel = hardwareMap.get(DcMotorEx.class, "outtake");

        // Set PIDF (start with defaults, tune later)
        flywheel.setVelocityPIDFCoefficients(0.2, 2.0, 0.002, 0.0);


        waitForStart();


        double time = 0;
        while (opModeIsActive()) {
            time += 0.02; // ~50 Hz loop


            //sine wave/variable setpoint between 2000 and 5000 ticks/sec
//            double targetVel = 3500 + 1500 * Math.sin(2 * Math.PI * 0.5 * time);


            // Send target to REV Hub PID
            flywheel.setVelocity(-targetVel);


            // Read actual velocity                
            double actualVel = flywheel.getVelocity();


            // Telemetry
            dashboardTelemetry.addData("Target (ticks/s): ", targetVel);
            dashboardTelemetry.addData("Actual (ticks/s): ", actualVel);
            dashboardTelemetry.addData("P:",p);
            dashboardTelemetry.addData("I:",i);
            dashboardTelemetry.addData("D:",d);
            dashboardTelemetry.update();

            TelemetryPacket packet = new TelemetryPacket();
            packet.fieldOverlay().setStroke("#3F51B5");
            FtcDashboard.getInstance().sendTelemetryPacket(packet);

            sleep(20);
        }
    }
}

