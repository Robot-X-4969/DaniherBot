package robotx.opmodes.testerOps;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.modules.opmode.FlyWheel;
import robotx.stx_libraries.XOpMode;
import robotx.stx_libraries.tuning.ConfigTester;


// sample change


@TeleOp(name = "DriveTester", group = "Tests")
public class TestMotorSpeed extends XOpMode {
    ConfigTester configTester;

    public void initModules() {

        super.initModules();

        FlyWheel flywheel = new FlyWheel(this);



    }

    public void init() {
        super.init();
    }
}