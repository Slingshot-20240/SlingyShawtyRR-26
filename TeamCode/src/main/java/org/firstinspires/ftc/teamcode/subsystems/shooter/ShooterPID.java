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

    DcMotorEx flywheel1;
    DcMotorEx flywheel2;
    public static double p1 = 578, i1 = 0.0, d1 = 0.0, f1 = 70;
    public static double p2 = 578, i2 = 0.0, d2 = 0.0, f2 = 70;
    public static int targetVel = -1095;
    private Telemetry dashboardTelemetry;
    Robot robot;
    GamepadMapping controls;
    FSM fsm;

    @Override
    public void init() {
        double time = 0;
        dashboardTelemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        flywheel1 = hardwareMap.get(DcMotorEx.class, "outtake1");
        flywheel2 = hardwareMap.get(DcMotorEx.class, "outtake2");

        // Set PIDF (start with defaults, tune later)
        flywheel1.setVelocityPIDFCoefficients(578, 0, 0, 70);
        flywheel2.setVelocityPIDFCoefficients(578, 0, 0, 70);
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, controls);

        flywheel1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flywheel2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        fsm = new FSM(hardwareMap, controls);
    }

    @Override
    public void loop() {
        //time += 0.02; // ~50 Hz loop

        // Updates driver controls here as well
        robot.drivetrain.update();
        // Updates all other controls
        controls.update();

        //robot.intake.intakeOn();

        if (controls.transfer.locked()) {
            robot.transfer.transferOn();
        } else {
            robot.transfer.hotDog();
        }

        if (controls.outtake.locked()) {
            robot.intake.intakeReverse();
        } else {
            robot.intake.intakeOn();
        }

        //sine wave/variable setpoint between 2000 and 5000 ticks/sec
        //double targetVel = 3500 + 1500 * Math.sin(2 * Math.PI * 0.5 * time);

      // Send target to REV Hub PID
        flywheel1.setVelocity(targetVel);
        flywheel2.setVelocity(targetVel);
        flywheel1.setVelocityPIDFCoefficients(p1, i1, d1, f1);
        flywheel2.setVelocityPIDFCoefficients(p2, i2, d2, f2);

        // Read actual velocity
        double actualVel = flywheel1.getVelocity();


        // Telemetry
        dashboardTelemetry.addData("Target (ticks/s): ", targetVel);
        dashboardTelemetry.addData("Actual (ticks/s): ", actualVel);
        dashboardTelemetry.addData("Encoder:", flywheel1.getCurrentPosition());
        dashboardTelemetry.addData("Encoder:", flywheel2.getCurrentPosition());
        dashboardTelemetry.update();

        // sleep(20);
    }
}

