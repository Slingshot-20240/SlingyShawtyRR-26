package org.firstinspires.ftc.teamcode.subsystems.vision;

import android.graphics.Canvas;
import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.opencv.core.Mat;
import org.opencv.core.Point;

public class logi {
    AprilTagProcessor apriltagPipeline;

    ballProcessor ballPipeline;

    VisionPortal portal;

    final double ATsize = 6.5;
    final double theta = Math.toRadians(45.2189284116);
    final double[] resolution = new double[]{640, 480};

    public logi(HardwareMap hw) {
        apriltagPipeline = new AprilTagProcessor.Builder().setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11).setDrawTagID(true).setDrawTagOutline(true).setDrawAxes(true).setDrawCubeProjection(true).setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES).build();
        ballPipeline = new ballProcessor();

        portal = new VisionPortal.Builder()
                .setCamera(hw.get(WebcamName.class, "Webcam 1"))
                .addProcessors(apriltagPipeline, ballPipeline)
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .setAutoStopLiveView(true)
                .enableLiveView(true)

                .build();

        portal.setProcessorEnabled(apriltagPipeline, true);
        portal.setProcessorEnabled(ballPipeline, false);

    }

    public void enableAT() {
        portal.setProcessorEnabled(apriltagPipeline, true);
    }

    public void enableAT(boolean enabled) {
        portal.setProcessorEnabled(apriltagPipeline, enabled);
    }

    public void enableBall() {
        portal.setProcessorEnabled(ballPipeline, true);
    }

    public void enableBall(boolean enabled) {
        portal.setProcessorEnabled(ballPipeline, enabled);
    }

    public double getATdist() {
        if(!portal.getProcessorEnabled(apriltagPipeline))
            return 0.0;

        for (AprilTagDetection detection : apriltagPipeline.getDetections()) {
            if (detection.id != 20 && detection.id != 24) continue;

            Point[] corners = detection.corners;
            double conversion = (Math.sqrt(Math.pow((corners[0].x - corners[1].x), 2) + Math.pow((corners[0].y - corners[1].y), 2)) + Math.sqrt(Math.pow((corners[2].x - corners[3].x), 2) + Math.pow((corners[2].y - corners[3].y), 2))) / 2.0;

            conversion = ATsize / conversion;

            double normalDist = resolution[0] / 2 * conversion / (Math.tan(theta / 2));

            //TODO: add the 3.5 inch offset
            double cX = detection.center.x;
            return Math.sqrt(Math.pow(normalDist, 2) + Math.pow((conversion * (cX - resolution[0] / 2)), 2));

        }
        return 0.0;
    }

    public double getATangle() {
        if(!portal.getProcessorEnabled(apriltagPipeline))
            return 0.0;

        for (AprilTagDetection detection : apriltagPipeline.getDetections()) {
            if (detection.id != 20 && detection.id != 24) continue;

            Point[] corners = detection.corners;
            double conversion = (Math.sqrt(Math.pow((corners[0].x - corners[1].x), 2) + Math.pow((corners[0].y - corners[1].y), 2)) + Math.sqrt(Math.pow((corners[2].x - corners[3].x), 2) + Math.pow((corners[2].y - corners[3].y), 2))) / 2.0;

            conversion = ATsize / conversion;

            double normalDist = resolution[0] / 2 * conversion / (Math.tan(theta / 2));


            double cX = detection.center.x;
            return Math.atan(conversion * (cX - resolution[0] / 2) / (normalDist));

        }
        return 0.0;
    }
}

//Not done yet sdadge
class ballProcessor implements VisionProcessor {

    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }
}