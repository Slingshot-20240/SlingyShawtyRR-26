package org.firstinspires.ftc.teamcode.subsystems.shooter.action;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class ShooterAction implements Action {
    DcMotorEx outtake;
    double outtakeVel;

    public ShooterAction(DcMotorEx outtake, double outtakeVel) {
        this.outtake = outtake;
        this.outtakeVel = outtakeVel;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        outtake.setVelocityPIDFCoefficients(575,0,0,70);
        outtake.setVelocity(outtakeVel);
        return false;
    }
}

