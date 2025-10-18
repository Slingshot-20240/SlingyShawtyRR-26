package org.firstinspires.ftc.teamcode.subsystems.shooter;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class ShooterAction implements Action {
    DcMotorEx outtake;
    double outtakePower;

    public ShooterAction(DcMotorEx outtake, double outtakePower) {
        this.outtake = outtake;
        this.outtakePower = outtakePower;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        outtake.setPower(outtakePower);
        return false;
    }
}