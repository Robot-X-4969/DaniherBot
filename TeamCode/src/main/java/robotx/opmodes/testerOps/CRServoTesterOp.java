package robotx.opmodes.testerOps;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import robotx.modules.opmode.CRServoTest;
import robotx.stx_libraries.XOpMode;
import robotx.stx_libraries.components.XCRServo;

@TeleOp(name = "CrServo", group = "Tests")
public class CRServoTesterOp extends XOpMode {

    public void initModules(){

        super.initModules();

        CRServoTest crServoTest = new CRServoTest(this);
        activeModules.add(crServoTest);
    }
}
