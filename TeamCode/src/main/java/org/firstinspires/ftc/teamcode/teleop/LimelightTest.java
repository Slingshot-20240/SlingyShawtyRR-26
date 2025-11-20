package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.subsystems.vision.PythonLimelight;

public class LimelightTest extends OpMode {

    public PythonLimelight limelight;

    @Override
    public void init() {
        limelight = new PythonLimelight(hardwareMap);
    }

    @Override
    public void loop() {
        telemetry.addData("limelight angle", Math.toDegrees(limelight.getAngle()));
        telemetry.addData("limelight nav", (limelight.getLastNav()));
        telemetry.addData("limelight obelisk", (limelight.getObelisk().order));
        telemetry.update();
    }
}
