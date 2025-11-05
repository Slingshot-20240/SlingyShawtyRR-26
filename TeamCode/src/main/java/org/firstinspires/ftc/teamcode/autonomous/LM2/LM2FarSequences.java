package org.firstinspires.ftc.teamcode.autonomous.LM2;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.shooter.action.ShooterAction;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;


public class LM2FarSequences {

    Intake intake;
    Transfer transfer;
    Shooter shooter;
    LM2HardwareSequences hws;

    LM2FarSequences(HardwareMap hardwareMap) {
        intake = new Intake(hardwareMap);
        transfer = new Transfer(hardwareMap);
        shooter = new Shooter(hardwareMap);
        hws = new LM2HardwareSequences(hardwareMap);
    }


    public Action scoreSet(double speedUpTime, double transferTime) {
        return new SequentialAction(

                intake.in(),
                new SequentialAction(
                        //TODO - Flywheel is already near speed, tune the time it takes to adjust. should be very low
                        new SleepAction(speedUpTime),
                        //TODO - Tune the transfer time to shoot 3 balls
                        hws.transferUpFor(transferTime),

                )

        );
    }


    /**
     * Intakes while hotdogging
     */
    public Action intakeSet(int shooterPower) {
        return new ParallelAction(
                intake.in(),
                transfer.hotdog(),
                new ShooterAction(shooter.outtake1, shooter.outtake2, shooterPower)

        );
    }


}

