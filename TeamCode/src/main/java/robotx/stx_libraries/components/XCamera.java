package robotx.stx_libraries.components;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.ArrayList;
import java.util.List;


public class XCamera {

    OpMode op;

    double tx;
    double ty;
    double ta;
    double txNC;
    double tyNC;



    ArrayList<Integer> aprilTagIDs = new ArrayList<>();
    ArrayList<Double> aprilTagTX = new ArrayList<>();
    ArrayList<Double> apriltagTY = new ArrayList<>();

    boolean isAprilTagResult = false;

    int index;
    private Limelight3A limeLight;
    final private String name;
    public XCamera(OpMode op, String name){
        this.op = op;
        this.name = name;
    }

    public void init(){
        limeLight = op.hardwareMap.get(Limelight3A.class, name);
        limeLight.start();
        index = 0;
        limeLight.pipelineSwitch(index); //0 for Green, 1 for Purple, 2 for April Tags
    }

    public void getData(){

        aprilTagIDs.clear();
        aprilTagTX.clear();
        apriltagTY.clear();

        LLResult result = limeLight.getLatestResult();
        if(index == 0 || index == 1){

            if(result != null && result.isValid()) {
                //op.telemetry.addData("X offset", result.getTx());
                //op.telemetry.addData("Y offset", result.getTy());
                //op.telemetry.addData("Area offset", result.getTa());
                //op.telemetry.addData("X deg", result.getTxNC());
                //op.telemetry.addData("Y deg", result.getTyNC());

                tx = result.getTx();
                ty = result.getTy();
                ta = result.getTa();
                txNC = result.getTxNC();
                tyNC = result.getTyNC();
            }
        }

        if(index == 2 && result != null && result.isValid()){
            List<LLResultTypes.FiducialResult> fResults = result.getFiducialResults();

            if(fResults != null){
                isAprilTagResult = true;
                for(LLResultTypes.FiducialResult fiducial : fResults){
                    aprilTagIDs.add(fiducial.getFiducialId());
                    Pose3D pose = fiducial.getTargetPoseRobotSpace();
                    double z = pose.getPosition().z;
                    double y = pose.getPosition().y;
                    double x = pose.getPosition().x;

                    double txDegrees = Math.toDegrees(Math.atan2(y, x));
                    double tyDegrees = Math.toDegrees(Math.atan2(z, x));

                    aprilTagTX.add(txDegrees);
                    apriltagTY.add(tyDegrees);

                }
            } else {
                isAprilTagResult = false;
            }

        }
    }

    public void changePipeline(int index){
        limeLight.pipelineSwitch(index);
        this.index = index;
    }

    public int getPipelineIndex(){
        return index;
    }

    public void loop(){
        getData();
        if(aprilTagIDs.size() > 0){
            op.telemetry.addData("ID", aprilTagIDs.get(0));
        }

    }

    public double getTx() {
        return tx;
    }

    public double getTy() {
        return ty;
    }

    public double getTa() {
        return ta;
    }

    public double getTxNC() {
        return txNC;
    }

    public double getTyNC() {
        return tyNC;
    }

    public boolean seesAprilTag(int targetID){
        for(int ID : aprilTagIDs){
            if(ID == targetID){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Double> getAprilTagTY() {
        return apriltagTY;
    }

    public ArrayList<Double> getAprilTagTX() {
        return aprilTagTX;
    }

    public ArrayList<Integer> getAprilTagIDs() {
        return aprilTagIDs;
    }

    public int getAprilTagIndex(int targetID){
        for(int i = 0; i < aprilTagIDs.size(); i++){
            if(aprilTagIDs.get(i) == targetID){
                return i;
            }
        }
        return -1;
    }
}
