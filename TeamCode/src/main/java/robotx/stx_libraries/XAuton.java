package robotx.stx_libraries;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.ArrayList;
import java.util.List;

public class XAuton extends LinearOpMode {
    public final XRobotContext ctx = new XRobotContext(this);
    public final List<XModule> modules = ctx.autonomousModules;

    public void initModules(){
    }

    public void initialize(){
        initModules();
        for(XModule module : modules){
            module.init();
        }
    }

    public void run(){
    }

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        if(opModeIsActive()){
            run();
        }
    }

    public void registerModule(XModule module){
        ctx.registerModule(module, XRobotContext.ModuleType.AUTONOMOUS);
    }
}
