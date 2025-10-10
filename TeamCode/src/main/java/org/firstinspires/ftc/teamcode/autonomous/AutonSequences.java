//package org.firstinspires.ftc.teamcode.autonomous;
//
//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
//
//import com.acmerobotics.roadrunner.Action;
//import com.acmerobotics.roadrunner.ParallelAction;
//import com.acmerobotics.roadrunner.SequentialAction;
//import com.acmerobotics.roadrunner.SleepAction;
//
//import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
//
//public class AutonSequences {
//
//    static Intake intake = new Intake(hardwareMap);
//
//    /**
//     * Lift up, Arm scores
//     */
//
//    public static Action scoreHigh() {
//        return new SequentialAction(
//                new ParallelAction(
//                        lift.toHighBasket(),
//                        extendo.out()
//                ),
//                new ParallelAction(
//                        arm.toScore(),
//                        clawPivot.toScore(),
//                        intakePivot.toIntake(),
//                        intake.in()
//                )
//        );
//    }
//
//    public static Action armClawScore() {
//        return new SequentialAction(
//                armClaw.open()
//        );
//    }
//
//    /**
//     * Arm, ClawPivot, and Lift ready for Transfer
//     */
//    public static Action readyForPickup() {
//        return new SequentialAction(
//                new ParallelAction(
//                        lift.toTransfer(),
//                        arm.toTransfer(),
//                        clawPivot.toTransfer()
//                )
//        );
//    }
//
//    /**
//     * Intake in for (seconds), then IntakePivot ready for transfer
//     * @param seconds - Time the intake runs for
//     */
//    public static Action pickUp(double seconds) {
//        return new SequentialAction(
//                intake.in(),
//                new SleepAction(seconds),
//
//                new ParallelAction(
//                        intake.idle(),
//                        intakePivot.toTransfer()
//                )
//        );
//    }
//
//    /**
//     * Extendo in, Claw grabs
//     */
//    public static Action transferBlock() {
//        return new SequentialAction(
//                extendo.in(),
//                //Move block to claw
//                new ParallelAction(
//                        moveBlockInClaw(),
//                        extendo.mini_out()
//                )
//        );
//    }
//
//    /**
//     * Pushes block towards claw for transfer
//     */
//    public static Action moveBlockInClaw() {
//        return new SequentialAction(
//                intake.out(),
//                new SleepAction(0.8),
//                armClaw.close(),
//                intake.idle()
//        );
//    }
//
//    public static Action scorePickup3() {
//        return new SequentialAction(
//                lift.toHighBasket(),
//
//                new ParallelAction(
//                        arm.toScore(),
//                        clawPivot.toScore()
//                )
//        );
//    }
//}
//*/
