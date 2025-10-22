package org.firstinspires.ftc.teamcode.subsystems.transfer;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Transfer {
    public final CRServo transferL;
    public final CRServo transferR;


    public Transfer(HardwareMap hardwareMap) {
        transferL = hardwareMap.get(CRServo.class, "transferL");
        transferR = hardwareMap.get(CRServo.class, "transferR");

        transferL.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    //----------------------------Up----------------------------------\\
    public class TransferUp implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            transferL.setPower(1.0);
            transferR.setPower(1.0);
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
            transferL.setPower(0);
            transferR.setPower(0);
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
            transferL.setPower(-0.4);
            transferR.setPower(-0.4);
            return false;
        }
    }
    public Action down() {
        return new TransferDown();
    }

}

