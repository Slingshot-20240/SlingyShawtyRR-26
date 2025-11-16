package org.firstinspires.ftc.teamcode.subsystems.transfer;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Transfer {
    public final CRServo backTransfer;
    public final CRServo frontTransfer;


    public Transfer(HardwareMap hardwareMap) {
        backTransfer = hardwareMap.get(CRServo.class, "transferB");
        frontTransfer = hardwareMap.get(CRServo.class, "transferF");

    }

    //----------------------------Up----------------------------------\\
    public class TransferUp implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            backTransfer.setPower(-1.0);
            frontTransfer.setPower(-1.0);
            return false;
        }
    }
    public Action on() {
        return new TransferUp();
    }

    //-----------------------------Idle--------------------------------------\\
    public class TransferIdle implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            backTransfer.setPower(0);
            frontTransfer.setPower(0);
            return false;
        }
    }
    public Action off() {
        return new TransferIdle();
    }

    //-----------------------------Down--------------------------------------\\
    public class TransferDown implements Action {

        //Changed in git online lol, to not set power full when transfer down. 
        //Most likely down will only be used for flow of balls etc. 
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            backTransfer.setPower(0.4);
            frontTransfer.setPower(0.4);
            return false;
        }
    }
    public Action down() {
        return new TransferDown();
    }

    //-----------------------------Hotdog--------------------------------------\\
    public class TransferHotdog implements Action {

        //Changed in git online lol, to not set power full when transfer down.
        //Most likely down will only be used for flow of balls etc.
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            backTransfer.setPower(-1);
            frontTransfer.setPower(-0.05);
            return false;
        }
    }
    public Action hotdog() {
        return new TransferHotdog();
    }

    public void transferOn() {
        backTransfer.setPower(-1.0);
        frontTransfer.setPower(-1.0);
    }

    public void transferOff() {
        backTransfer.setPower(0);
        frontTransfer.setPower(0);
    }

    public void hotDog() {
        backTransfer.setPower(1);
        frontTransfer.setPower(-0.2);
    }

}

