package org.firstinspires.ftc.teamcode.subsystems.vision;

import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import java.util.Objects;
import java.util.stream.*;


public class PythonLimelight {
    /*
    ACUTAL FORMAL:
    [
        most recent obelisk ID,
        most recent nav ID,
        angle from change yes (if the marker is on the right, then -, if on left, +)
    ]

    IDEAL FORMAT:
    [
        most recent obelisk ID,
        most recent nav ID,
        relocalized x based on nav ID,
        relocalized y,
        relocalized z,
        relocalized yaw,
        relocalized pitch,
        relocalized roll
    ]
    */
    Limelight3A limelight;

    public PythonLimelight(HardwareMap hw) {
        limelight = hw.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(1);
        limelight.start();
    }
    public ObeliskLocation getObelisk(){
        return ObeliskLocation.fromInt((int) limelight.getLatestResult().getPythonOutput()[0]);
    }

    //neg for turn right (clockwise), pos for turn left (counter clockwise) [radians]
    public double getAngle(){
        double[] llr = limelight.getLatestResult().getPythonOutput();

        double angle = llr[2];
        if(angle == -1)
            return -1.0;

        return angle;
    }
    /*
    public Pose3D getPose() {
        double[] llResults = limelight.getLatestResult().getPythonOutput();
        return new Pose3D(new Position(DistanceUnit.INCH, //TODO: figure this out LOL
                                       llResults[2],
                                       llResults[3],
                                       llResults[4],
                                       System.nanoTime()),

                          new YawPitchRollAngles(AngleUnit.DEGREES, //TODO: figure this out LOLOLLLL
                                                 llResults[5],
                                                 llResults[6],
                                                 llResults[7],
                                                 System.nanoTime()));
    }
    */
    public enum ObeliskLocation //measured by the location of the green
    {
        LEFT("GPP", 21), CENTER("PGP", 22), RIGHT("PPG", 23);

        public final String order;
        public final int ATnumber;
        ObeliskLocation(String o, int AT){
            order = o;
            ATnumber = AT;
        }

        public static ObeliskLocation fromInt(int i){
            for(ObeliskLocation ol : ObeliskLocation.values()){
                if (ol.ATnumber == i)
                    return ol;
            }
            return null;
        }
    }

}
