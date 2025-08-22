package robotx.opmodes.testerOps;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.modules.opmode.RPMMeasure;
import robotx.stx_libraries.XOpMode;
import robotx.stx_libraries.tuning.ConfigTester;


// sample change


@TeleOp(name = "RPMTester", group = "Tests")
public class RPMTester extends XOpMode {
    RPMMeasure rpmMeasure;

    public void initModules() {

        super.initModules();

        rpmMeasure = new RPMMeasure(this);
        activeModules.add(rpmMeasure);
    }

    public void init() {
        super.init();
    }
}