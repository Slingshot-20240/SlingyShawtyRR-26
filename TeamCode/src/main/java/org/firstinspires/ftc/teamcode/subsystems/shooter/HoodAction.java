package org.firstinspires.ftc.teamcode.subsystems.shooter;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

//PROGRAM CHANGED BY ISHAAN 10/22 ON GIT IDE on chromebook lol
//I cannot see errors, so TODO: SUPRESS ERRORS
public class HoodAction implements Action {
    Servo variableHood;

    ElapsedTime timer;
    double position;


    public HoodAction(Servo variableHood, double position) {
        this.variableHood = variableHood;
        this.position = position;

        variableHood.setDirection(Servo.Direction.REVERSE);
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        if(timer == null) {
            timer = new ElapsedTime();
            variableHood.setPosition(position);
        }

        //How long you are giving it to do the action (moving the servos) before returning out of it
        return timer.seconds() < 0.2;
    }
}