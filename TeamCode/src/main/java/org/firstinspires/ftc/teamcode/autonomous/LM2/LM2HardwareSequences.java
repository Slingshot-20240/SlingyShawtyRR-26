package org.firstinspires.ftc.teamcode.autonomous.LM2;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

//TODO - GUYS TRY TYPING "HARDWARE SEQUENCES"
// ISN'T IT SO COOL AND SATISFYING HOW YOUR LEFT HAND TYPES IT ALL WOW!! heh anyway back to coding rip
public class LM2HardwareSequences {

    Intake intake;
    Transfer transfer;
    Shooter shooter;

    LM2HardwareSequences(HardwareMap hardwareMap) {
        intake = new Intake(hardwareMap);
        transfer = new Transfer(hardwareMap);
        shooter = new Shooter(hardwareMap);
    }


    public Action intakeInFor(double time) {
        return new SequentialAction(
                intake.in(),
                new SleepAction(time),
                intake.idle()
        );
    }

    public Action transferUpFor(double time) {
        return new SequentialAction(
                transfer.on(),
                new SleepAction(time),
                transfer.off()
        );
    }

    public Action shootFor(double time) {
        return new SequentialAction(
                shooter.out(),
                new SleepAction(time),
                shooter.idle()
        );
    }



}
