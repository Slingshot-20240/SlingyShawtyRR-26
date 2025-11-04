package org.firstinspires.ftc.teamcode.subsystems.intake;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class IntakeAction implements Action {
    private final DcMotorEx intake;
    private final double intakePower;

    public IntakeAction(DcMotorEx intake, double intakePower) {
        this.intake = intake;
        this.intakePower = intakePower;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        intake.setPower(intakePower);

        return true;
    }
}
