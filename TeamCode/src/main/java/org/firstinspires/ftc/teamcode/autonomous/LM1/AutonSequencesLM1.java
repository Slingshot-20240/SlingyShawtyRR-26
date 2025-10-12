package org.firstinspires.ftc.teamcode.autonomous.LM1;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.HoodAction;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterAction;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;


public class AutonSequencesLM1 {

    Intake intake;
    Transfer transfer;
    Shooter shooter;
    HardwareSequences hws;

    AutonSequencesLM1(HardwareMap hardwareMap) {
        intake = new Intake(hardwareMap);
        transfer = new Transfer(hardwareMap);
        shooter = new Shooter(hardwareMap);
        hws = new HardwareSequences(hardwareMap);
    }


    /**
     * Code todo
     * keep in mind to wait between each shot
     * how do we figure out if a ball has been shot or not with CR servo
     * Transfer only turns on once shooter power has been set
     * Intake power runs constantly
     * Hood is angled to shoot position
     */
    public Action scoreSet() {
        return new ParallelAction(
                //TODO - not done 🥀 rip lock tf in ishaan quit slacking

                intake.in(),
                new SequentialAction(
                        new ShooterAction(shooter.outtake, 0.8),
                        new ParallelAction(
                                new HoodAction(shooter.variableHoodL, shooter.variableHoodR, 0.5),
                                transfer.on()
                        )
                )

        );
    }


    //TODO - for both intakeSet AND prepare for Set should we hardcode values here or keep parameters
    // because then they would both be the same function, intaking while transferring
    /**
     * Intakes while transfer
     */
    public Action intakeSet() {
        return new ParallelAction(
                intake.in(),
                hws.transferUpFor(3)
        );
    }

    /**
     * Intake in while transfer
     */
    public Action prepareForSet(long intakeTime, long transferTime) {
        return new ParallelAction(
                hws.intakeInFor(intakeTime),
                hws.transferUpFor(transferTime)
        );
    }



}

