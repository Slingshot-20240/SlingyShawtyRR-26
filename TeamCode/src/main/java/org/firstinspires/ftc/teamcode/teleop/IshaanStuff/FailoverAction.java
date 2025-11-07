package org.firstinspires.ftc.teamcode.teleop.IshaanStuff;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

/**
 * Runs mainAction until failover() is called, then runs failoverAction.
 * Generic utility used by the cancellation pattern in the RR docs.
 */
public class FailoverAction implements Action {
    private final Action mainAction;
    private final Action failoverAction;
    private boolean failedOver = false;

    public FailoverAction(Action mainAction, Action failoverAction) {
        this.mainAction = mainAction;
        this.failoverAction = failoverAction;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        if (failedOver) {
            return failoverAction.run(telemetryPacket);
        }
        return mainAction.run(telemetryPacket);
    }

    /**
     * Call to switch from mainAction to failoverAction.
     */
    public void failover() {
        failedOver = true;
    }

    @Override
    public void preview(com.acmerobotics.dashboard.canvas.Canvas canvas) {
        // Optional: delegate preview if available on underlying actions.
        try {
            mainAction.getClass().getMethod("preview", com.acmerobotics.dashboard.canvas.Canvas.class)
                    .invoke(mainAction, canvas);
        } catch (Exception ignored) {}
        try {
            failoverAction.getClass().getMethod("preview", com.acmerobotics.dashboard.canvas.Canvas.class)
                    .invoke(failoverAction, canvas);
        } catch (Exception ignored) {}
    }
}
