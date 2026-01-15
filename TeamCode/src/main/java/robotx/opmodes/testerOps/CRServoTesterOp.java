package robotx.opmodes.testerOps;

import robotx.modules.opmode.CRServoTest;
import robotx.stx_libraries.XOpMode;
import robotx.stx_libraries.components.XCRServo;

public class CRServoTesterOp extends XOpMode {

    public void initModules(){
        
        super.initModules();

        CRServoTest crServoTest = new CRServoTest(this);
        activeModules.add(crServoTest);
    }
}
