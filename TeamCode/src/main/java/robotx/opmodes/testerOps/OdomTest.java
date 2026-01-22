package robotx.opmodes.testerOps;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.modules.opmode.PositionTracker;
import robotx.stx_libraries.XOpMode;

@TeleOp(name = "OdomTest", group = "Tests")
public class OdomTest extends XOpMode {

    PositionTracker pt = new PositionTracker(this);

    @Override
    public void initModules(){
        super.initModules();
        registerModule(pt);

    }

}
