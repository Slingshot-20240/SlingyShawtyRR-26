package org.firstinspires.ftc.teamcode.subsystems.shooter.action;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

public class ShooterReachVelAction implements Action {
    DcMotorEx outtake;
    Transfer transfer;
    double outtakeVel;

    public ShooterReachVelAction(DcMotorEx outtake, Transfer transfer, double outtakeVel) {
        this.outtake = outtake;
        this.outtakeVel = outtakeVel;
        this.transfer = transfer;

    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        outtake.setVelocityPIDFCoefficients(0,0.44,0.011,0);

        if (outtake.getVelocity() <= outtakeVel - 10 || outtake.getVelocity() >= outtakeVel + 10) {
            outtake.setVelocity(outtakeVel);
            return false;
        } else {
            transfer.backReverseFrontForward();
            return true;
        }
    }
}
