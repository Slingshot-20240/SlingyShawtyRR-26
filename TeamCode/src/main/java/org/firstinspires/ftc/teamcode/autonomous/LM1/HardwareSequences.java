package org.firstinspires.ftc.teamcode.autonomous.LM1;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;

import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

//TODO - GUYS TRY TYPING "HARDWARE SEQUENCES"
// ISN'T IT SO COOL AND SATISFYING HOW YOUR LEFT HAND TYPES IT ALL WOW!! heh anyway back to coding rip

public class HardwareSequences {

    static Intake intake = new Intake(hardwareMap);
    static Transfer transfer = new Transfer(hardwareMap);
    static Shooter shooter = new Shooter(hardwareMap);

    /**
     * Lift up, Arm scores
     */
    public static Action intakeInFor(long time) {
        return new SequentialAction(
                intake.in(),
                new SleepAction(time),
                intake.idle()
        );
    }

    public static Action transferUpFor(long time) {
        return new SequentialAction(
                transfer.on(),
                new SleepAction(time),
                transfer.off()
        );
    }



}
