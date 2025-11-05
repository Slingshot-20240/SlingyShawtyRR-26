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



    public Action scorePreloads() {
        return new SequentialAction(

                intake.in(),
                new SequentialAction(
                        //TODO - Tune the time the flywheel tune to get to good speed
                        new SleepAction(2.5),
                        hws.transferUpFor(4)

                )

        );
    }



    public Action scoreSet() {
        return new SequentialAction(

                intake.in(),
                new SequentialAction(
                        //TODO - Tune the time the flywheel tune to get to good speed
                        new SleepAction(0.5),
                        //TODO - Tune the transfer time to shoot 3 balls
                        hws.transferUpFor(4)
                )

        );
    }


    //TODO - for both intakeSet AND prepare for Set should we hardcode values here or keep parameters
    // because then they would both be the same function, intaking while transferring
    /**
     * Intakes while transfer
     */
    public Action intakeSet() {
        return new SequentialAction(
                new ParallelAction(
                        intake.in(),
                        transfer.hotdog()
                )

        );
    }


}

