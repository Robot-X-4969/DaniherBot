package robotx.stx_libraries.components;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import robotx.stx_libraries.XModule;

public class XCamera {

    OpMode op;
    private Limelight3A limeLight;
    final private String name;

    public XCamera(OpMode op, String name){
        this.op = op;
        this.name = name;
    }

    public void init(){
        limeLight = op.hardwareMap.get(Limelight3A.class, name);
        limeLight.start();
        limeLight.pipelineSwitch(0); //0 for Green, 1 for Purple, 2 for April Tags
    }

    public void getData(){
        LLResult result = limeLight.getLatestResult();
        if(result != null && result.isValid()){
            op.telemetry.addData("X offset", result.getTx());
            op.telemetry.addData("Y offset", result.getTy());
            op.telemetry.addData("Area offset", result.getTa());
            op.telemetry.addData("X deg", result.getTxNC());
            op.telemetry.addData("Y deg", result.getTyNC());
        }
    }

    public void changePipeline(int index){
        limeLight.pipelineSwitch(index);
    }
}
