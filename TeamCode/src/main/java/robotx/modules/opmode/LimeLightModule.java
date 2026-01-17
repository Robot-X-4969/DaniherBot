package robotx.modules.opmode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import robotx.stx_libraries.XModule;

public class LimeLightModule extends XModule {


    private Limelight3A limeLight;
    public LimeLightModule(OpMode op){
        super(op);
    }

    @Override
    public void init(){
        limeLight = opMode.hardwareMap.get(Limelight3A.class, "Limelight");
        limeLight.pipelineSwitch(2); //0 is Green and 1 is Purple
    }

    public void start(){
        limeLight.start();

    }

    @Override
    public void loop(){
        LLResult result = limeLight.getLatestResult();
        if(result != null && result.isValid()){
            opMode.telemetry.addData("X offset", result.getTx());
            opMode.telemetry.addData("Y offset", result.getTy());
            opMode.telemetry.addData("Area offset", result.getTa());
            opMode.telemetry.addData("X deg", result.getTxNC());
            opMode.telemetry.addData("Y deg", result.getTyNC());

        }
    }


}
