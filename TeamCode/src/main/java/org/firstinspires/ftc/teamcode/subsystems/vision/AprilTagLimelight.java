package org.firstinspires.ftc.teamcode.subsystems.vision;

import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.Objects;
import java.util.stream.*;


public class AprilTagLimelight {
    Limelight3A limelight;
    boolean isBlue;

    public AprilTagLimelight(HardwareMap hw, boolean isBlue) {
        limelight = hw.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(1);
        limelight.start();
        this.isBlue = isBlue;
    }
    public ObeliskLocation getObelisk(){
        return Stream.of(limelight.getLatestResult().getFiducialResults()).map((a)->(FiducialResult)a).filter(Objects::nonNull).map(a->a.getFiducialId()).map(ObeliskLocation::fromInt).filter(Objects::nonNull).findFirst().orElse(null);
    }
    public Pose3D getDist() {
        return Stream.of(limelight.getLatestResult().getFiducialResults()).map((a) -> (FiducialResult) a).filter(Objects::nonNull).filter((a) -> (!isBlue && a.getFiducialId() == 24) || (isBlue && a.getFiducialId() == 20).map(FiducialResult::getRobotPoseTargetSpace).findFirst().orElse(null); // If loop time is bad, I can fix
    }
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
