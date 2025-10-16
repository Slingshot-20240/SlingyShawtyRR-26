package org.firstinspires.ftc.teamcode.subsystems.shooter;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class ShooterAction implements Action {
    DcMotorEx outtakeL;
    DcMotorEx outtakeR;
    double outtakePower;

    public ShooterAction(DcMotorEx outtakeL, DcMotorEx outtakeR, double outtakePower) {
        this.outtakeL = outtakeL;
        this.outtakeR = outtakeR;
        this.outtakePower = outtakePower;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        outtakeL.setPower(outtakePower);
        outtakeR.setPower(outtakePower);

        return false;
    }
}