package org.firstinspires.ftc.teamcode.subsystems.shooter.action;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class ShooterAction implements Action {
    DcMotorEx outtake1;
    DcMotorEx outtake2;
    double outtakeVel;

    public ShooterAction(DcMotorEx outtake1, DcMotorEx outtake2, double outtakeVel) {
        this.outtake1 = outtake1;
        this.outtake2 = outtake2;
        this.outtakeVel = outtakeVel;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        outtake1.setVelocityPIDFCoefficients(575,0,0,70);
        outtake2.setVelocityPIDFCoefficients(575,0,0,70);
        outtake1.setVelocity(outtakeVel);
        outtake2.setVelocity(outtakeVel);
        return false;
    }
}

