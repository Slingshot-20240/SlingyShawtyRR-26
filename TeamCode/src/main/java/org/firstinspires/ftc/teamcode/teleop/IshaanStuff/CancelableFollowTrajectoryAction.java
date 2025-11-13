//package org.firstinspires.ftc.teamcode.teleop.IshaanStuff;
//
//import androidx.annotation.NonNull;
//
//import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
//import com.acmerobotics.roadrunner.Action;
//import com.acmerobotics.roadrunner.InstantAction;
//import com.acmerobotics.roadrunner.TimeTrajectory;
//
//import org.firstinspires.ftc.teamcode.MecanumDrive;
//
//
///**
// * A cancelable wrapper around FollowTrajectoryAction that can be cancelled abruptly.
// * On cancel it switches to an instant action which sets drivetrain powers to zero.
// *
// * NOTE: adjust imports (FollowTrajectoryAction location) to match your repo.
// */
//public class CancelableFollowTrajectoryAction implements Action {
//    private final Action failoverStop;
//    private final MecanumDrive.FollowTrajectoryAction action;
//    private volatile boolean cancelled = false;
//
//    public CancelableFollowTrajectoryAction(TimeTrajectory t) {
//        // FollowTrajectoryAction runs the trajectory normally.
//        action = new MecanumDrive.FollowTrajectoryAction(t);
//
//        // Failover action: an InstantAction which sets drive powers to zero.
//        failoverStop = new InstantAction(() -> {
//            // setDrivePowers is project-specific — replace with your drive stop call.
//            // Example (if using a static drive instance): Drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0,0), 0));
//            // We'll call a small helper stub below; replace as needed.
//            stopDriveImmediately();
//        });
//    }
//
//    @Override
//    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//        if (cancelled) {
//            return failoverStop.run(telemetryPacket);
//        }
//        return action.run(telemetryPacket);
//    }
//
//    public void cancelAbruptly() {
//        cancelled = true;
//    }
//
//    @Override
//    public void preview(com.acmerobotics.dashboard.canvas.Canvas canvas) {
//        // delegate preview to wrapped action so the dashboard can show the path
//        try {
//            action.getClass().getMethod("preview", com.acmerobotics.dashboard.canvas.Canvas.class)
//                    .invoke(action, canvas);
//        } catch (Exception ignored) {}
//    }
//
//    // ---- PROJECT-SPECIFIC STOP: replace with your drive's immediate stop method ----
//    private void stopDriveImmediately() {
//        // Example placeholder: call your Drive instance to set zero velocities.
//        // Drive.getInstance().setDrivePowers(new PoseVelocity2d(new Vector2d(0,0), 0));
//    }
//}
