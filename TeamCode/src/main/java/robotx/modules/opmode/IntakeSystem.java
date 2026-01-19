package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import robotx.stx_libraries.XModule;
import robotx.stx_libraries.components.XCRServo;


public class IntakeSystem extends XModule {

    XCRServo crServo1;
    XCRServo crServo2;

    public IntakeSystem(OpMode op) {
        super(op);
    }

    @Override
    public void init() {

        crServo1 = new XCRServo(opMode, "intakeServo1");
        crServo2 = new XCRServo(opMode, "intakeServo2");
        crServo1.init();
        crServo2.init();

    }

    @Override
    public void control_loop() {
        super.control_loop();

        if(dualPlayer){
            if(xGamepad1.a.isDown() ||xGamepad2.a.isDown()){
                crServo1.rotate(1.0);
                crServo2.rotate(-1.0);
            } else {
                crServo1.rotate(0.0);
                crServo2.rotate(-0.0);
            }
        } else {
            if(xGamepad1.left_trigger >= 0.25){
                crServo1.rotate(1.0);
                crServo2.rotate(-1.0);
            } else {
                crServo1.rotate(0.0);
                crServo2.rotate(-0.0);
            }
        }
    }

}
