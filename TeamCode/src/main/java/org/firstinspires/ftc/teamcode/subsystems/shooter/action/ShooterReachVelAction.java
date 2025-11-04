package org.firstinspires.ftc.teamcode.subsystems.shooter.action;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

public class ShooterReachVelAction implements Action {
    DcMotorEx outtake1;
    DcMotorEx outtake2;
    Transfer transfer;
    double outtakeVel;

    public ShooterReachVelAction(DcMotorEx outtake1, DcMotorEx outtake2, Transfer transfer, double outtakeVel) {
        this.outtake1 = outtake1;
        this.outtake2 = outtake2;
        this.outtakeVel = outtakeVel;
        this.transfer = transfer;

    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        outtake1.setVelocityPIDFCoefficients(0,0.44,0.011,0);
        outtake2.setVelocityPIDFCoefficients(0,0.44,0.011,0);

        if (outtake1.getVelocity() <= outtakeVel - 10 || outtake1.getVelocity() >= outtakeVel + 10) {
            outtake1.setVelocity(outtakeVel);
            outtake2.setVelocity(outtakeVel);
            return false;
        } else {
            transfer.hotDog();
            return true;
        }
    }
}
