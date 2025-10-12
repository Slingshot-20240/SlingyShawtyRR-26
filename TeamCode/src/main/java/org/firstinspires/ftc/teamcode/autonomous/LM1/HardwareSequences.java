package org.firstinspires.ftc.teamcode.autonomous.LM1;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

//TODO - GUYS TRY TYPING "HARDWARE SEQUENCES"
// ISN'T IT SO COOL AND SATISFYING HOW YOUR LEFT HAND TYPES IT ALL WOW!! heh anyway back to coding rip
public class HardwareSequences {

    Intake intake;
    Transfer transfer;
    Shooter shooter;

    HardwareSequences(HardwareMap hardwareMap) {
        intake = new Intake(hardwareMap);
        transfer = new Transfer(hardwareMap);
        shooter = new Shooter(hardwareMap);
    }

    /**
     * Lift up, Arm scores
     */
    public Action intakeInFor(long time) {
        return new SequentialAction(
                intake.in(),
                new SleepAction(time),
                intake.idle()
        );
    }

    public Action transferUpFor(long time) {
        return new SequentialAction(
                transfer.on(),
                new SleepAction(time),
                transfer.off()
        );
    }

    public Action shootFor(long time) {
        return new SequentialAction(
                shooter.out(),
                new SleepAction(time),
                shooter.idle()
        );
    }



}
