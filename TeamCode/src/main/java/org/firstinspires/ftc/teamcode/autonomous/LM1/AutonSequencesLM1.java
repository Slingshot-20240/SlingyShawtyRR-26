package org.firstinspires.ftc.teamcode.autonomous.LM1;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;

import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;


public class AutonSequencesLM1 {

    static Intake intake = new Intake(hardwareMap);
    static Transfer transfer = new Transfer(hardwareMap);
    static Shooter shooter = new Shooter(hardwareMap);

    /**
     * Code todo
     * keep in mind wait between each shot
     * how do we figure out if a ball has been shot or not with CR servo
     */
    public static Action scoreSet() {
        return new SequentialAction(
                //TODO - Code goes here 🥀 not done rip lock tf in ishaan quit slacking
        );
    }


    //TODO - for both intakeSet AND prepare for Set should we hardcode values here or keep parameters
    // because then they would both be the same function... both types are shown below
    /**
     * Intakes while transfer
     */
    public static Action intakeSet() {
        return new ParallelAction(
                HardwareSequences.intakeInFor(3),
                HardwareSequences.transferUpFor(3)
        );
    }

    /**
     * Intake in while transfer
     */
    public static Action prepareForSet(long intakeTime, long transferTime) {
        return new ParallelAction(
                HardwareSequences.intakeInFor(intakeTime),
                HardwareSequences.transferUpFor(transferTime)
        );
    }




}

