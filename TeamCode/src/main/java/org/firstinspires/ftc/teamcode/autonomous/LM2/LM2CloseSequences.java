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


public class LM2CloseSequences {

    Intake intake;
    Transfer transfer;
    Shooter shooter;
    LM2HardwareSequences hws;

    LM2CloseSequences(HardwareMap hardwareMap) {
        intake = new Intake(hardwareMap);
        transfer = new Transfer(hardwareMap);
        shooter = new Shooter(hardwareMap);
        hws = new LM2HardwareSequences(hardwareMap);
    }


    public Action preparePreloads() {
        return new ParallelAction(
                intake.in(),
                //shooter.out()
                new ShooterAction(shooter.outtake, -945)

        );
    }
    public Action scorePreloads() {
        return new SequentialAction(

                intake.in(),
                new SequentialAction(
                        //TODO - Tune the time the flywheel tune to get to good speed
                        new SleepAction(3.3),
                        hws.transferUpFor(2),
                        //new HoodAction(shooter.variableHood, 0.22),
                        hws.transferUpFor(5)
                        //shooter.idle()

                )


        );
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

                intake.in(),
                new SequentialAction(
                        //TODO - Tune the time the flywheel tune to get to good speed
                        new SleepAction(2),
                        hws.transferUpFor(6.7),
                        shooter.idle()

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
                        transfer.hotdog(),
                        intake.in()
                //TODO - Tune this transfer value to determine how high up the first ball goes
                       // transfer.hotdog()
                ),
                shooter.out()

        );
    }

    /**
     * Intake in while transfer
     */
    public Action prepareForSet() {
        return new ParallelAction(
                intake.in(),
                transfer.hotdog(),
                shooter.out()
        );
    }

}

