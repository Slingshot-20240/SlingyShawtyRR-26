package org.firstinspires.ftc.teamcode.subsystems.intake;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    public final DcMotorEx intake;


    public Intake(HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        intake.setVelocityPIDFCoefficients(0,0,0,0);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    //-----------------------------In--------------------------------------\\
    public class IntakeIn implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            intake.setPower(1.0);
            return false;
        }
    }
    public Action in() {
        return new IntakeIn();
    }
    //-----------------------------Idle--------------------------------------\\
    public class IntakeIdle implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            intake.setPower(0.0);
            return false;
        }
    }
    public Action idle() {
        return new IntakeIdle();
    }

    //-----------------------------Out--------------------------------------\\
    public class IntakeOut implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            intake.setPower(-1.0);
            return false;
        }
    }
    public Action out() {
        return new IntakeOut();
    }
//-------------------------------------------------------------------------------

    public void intakeOn() {
        intake.setPower(1);
    }

    public void intakeOff() {
        intake.setPower(0);
    }

}



