package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import robotx.stx_libraries.XModule;
import robotx.stx_libraries.components.XCRServo;

public class CRServoTest extends XModule {

    XCRServo crServo;

    public CRServoTest(OpMode op) {
        super(op);
    }

    public void init(){
        crServo = new XCRServo(opMode, "crServo");
        crServo.init();
    }

    @Override
    public void loop(){
        super.loop();

        crServo.rotate(1.0);

    }
}
