package org.firstinspires.ftc.teamcode.subsystems.shooter;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class HoodAction implements Action {
    Servo variableHoodL;
    Servo variableHoodR;

    ElapsedTime timer;
    double position;


    public HoodAction(Servo variableHoodL, Servo variableHoodR, double position) {
        this.variableHoodL = variableHoodL;
        this.variableHoodR = variableHoodR;
        this.position = position;

        variableHoodL.setDirection(Servo.Direction.FORWARD);
        variableHoodR.setDirection(Servo.Direction.REVERSE);
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        if(timer == null) {
            timer = new ElapsedTime();
            variableHoodL.setPosition(position);
            variableHoodR.setPosition(position);

        }

        //How long you are giving it to do the action (moving the servos) before returning out of it
        return timer.seconds() < 0.2;
    }
}