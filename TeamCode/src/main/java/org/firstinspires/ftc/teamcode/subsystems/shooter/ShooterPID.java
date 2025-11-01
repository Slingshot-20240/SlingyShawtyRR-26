package org.firstinspires.ftc.teamcode.subsystems.shooter;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.firstinspires.ftc.teamcode.subsystems.robot.Robot;
import org.firstinspires.ftc.teamcode.teleop.fsm.FSM;


@Config
@TeleOp(name = "ShooterPID", group = "Testing")
public class ShooterPID extends OpMode {

    DcMotorEx flywheel;
    public static double p = 0.2, i = 2.0, d = 0.002, f = 0.0;
    public static int targetVel = 500;
    private Telemetry dashboardTelemetry;
    Robot robot;
    GamepadMapping controls;
    FSM fsm;

    @Override
    public void init() {
        double time = 0;
        dashboardTelemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        flywheel = hardwareMap.get(DcMotorEx.class, "outtake");

        // Set PIDF (start with defaults, tune later)
        flywheel.setVelocityPIDFCoefficients(575, 0, 0, 70);
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, controls);

        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        fsm = new FSM(hardwareMap, controls);
    }

    @Override
    public void loop() {
        //time += 0.02; // ~50 Hz loop

        // Updates driver controls here as well
        robot.drivetrain.update();
        // Updates all other controls
        controls.update();

        robot.intake.intakeOn();

        if (controls.transfer.value()) {
            robot.transfer.transferOn();
        } else {
            robot.transfer.hotDog();
        }

        //sine wave/variable setpoint between 2000 and 5000 ticks/sec
        //double targetVel = 3500 + 1500 * Math.sin(2 * Math.PI * 0.5 * time);

      // Send target to REV Hub PID
        flywheel.setVelocity(targetVel);
        flywheel.setVelocityPIDFCoefficients(p, i, d, f);

        // Read actual velocity
        double actualVel = flywheel.getVelocity();


        // Telemetry
        dashboardTelemetry.addData("Target (ticks/s): ", targetVel);
        dashboardTelemetry.addData("Actual (ticks/s): ", actualVel);
        dashboardTelemetry.addData("P:",p);
        dashboardTelemetry.addData("I:",i);
        dashboardTelemetry.addData("D:",d);
        dashboardTelemetry.addData("Encoder:",flywheel.getCurrentPosition());
        dashboardTelemetry.update();

        // sleep(20);
    }
}

