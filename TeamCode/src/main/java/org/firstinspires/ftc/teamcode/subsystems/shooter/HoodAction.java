package org.firstinspires.ftc.teamcode.subsystems.shooter;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class HoodAction implements Action {

    Servo variableHood;
    ElapsedTime timer;
    double hoodPos;


    public HoodAction(Servo variableHood, double hoodPos) {
        this.variableHood = variableHood;
        this.hoodPos = hoodPos;

        variableHood.setDirection(Servo.Direction.REVERSE);
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        if(timer == null) {
            timer = new ElapsedTime();
            variableHood.setPosition(hoodPos);
        }

        return timer.seconds() < 0.2;
    }
}