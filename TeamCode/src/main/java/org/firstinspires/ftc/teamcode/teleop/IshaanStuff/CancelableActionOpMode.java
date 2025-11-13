//package org.firstinspires.ftc.teamcode.teleop.IshaanStuff;
//
//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
//import com.acmerobotics.roadrunner.TimeTrajectory;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//
///**
// * Example LinearOpMode that executes a cancelable trajectory and cancels on gamepad button press.
// * Adapt drive initialization and trajectory creation to your codebase.
// */
//@Autonomous(name = "Cancelable Trajectory Example")
//public class CancelableActionOpMode extends LinearOpMode {
//    @Override
//    public void runOpMode() throws InterruptedException {
//        FtcDashboard dash = FtcDashboard.getInstance();
//
//        // Build or obtain a TimeTrajectory (example placeholder).
//        TimeTrajectory traj = buildExampleTrajectory();
//
//        CancelableFollowTrajectoryAction cancelableAction =
//                new CancelableFollowTrajectoryAction(traj);
//
//        waitForStart();
//        if (isStopRequested()) return;
//
//        while (opModeIsActive() && !isStopRequested()) {
//            TelemetryPacket packet = new TelemetryPacket();
//
//            // let the action preview its trajectory to the dashboard
//            cancelableAction.preview(packet.fieldOverlay());
//
//            // run the action; if it returns false the action is complete
//            if (!cancelableAction.run(packet)) {
//                break;
//            }
//
//            // cancel when the driver presses 'A'
//            if (gamepad1.a) {
//                cancelableAction.cancelAbruptly();
//            }
//
//            dash.sendTelemetryPacket(packet);
//        }
//    }
//
//    private TimeTrajectory buildExampleTrajectory() {
//        // Replace this with your actual TimeTrajectory construction code.
//        return null;
//    }
//}
