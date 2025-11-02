package org.firstinspires.ftc.teamcode.autonomous.LM1;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.shooter.action.ShooterAction;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;


public class LM1FarSequences {

    Intake intake;
    Transfer transfer;
    Shooter shooter;
    HardwareSequences hws;

    LM1FarSequences(HardwareMap hardwareMap) {
        intake = new Intake(hardwareMap);
        transfer = new Transfer(hardwareMap);
        shooter = new Shooter(hardwareMap);
        hws = new HardwareSequences(hardwareMap);
    }


    public Action preparePreloads() {
        return new ParallelAction(
                intake.in(),
                //shooter.out()
                new ShooterAction(shooter.outtake, -1387)

        );
    }
    public Action scorePreloads() {
        return new SequentialAction(

                intake.in(),
                new SequentialAction(
                        //TODO - Tune the time the flywheel tune to get to good speed
                        new SleepAction(6),
                        hws.transferUpFor(7)
                        //hws.transferUpFor(5)

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
                        new SleepAction(1),
                        hws.transferUpFor(7),
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
                )
                //shooter.out()

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

