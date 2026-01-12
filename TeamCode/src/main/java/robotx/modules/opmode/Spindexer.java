package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import robotx.stx_libraries.XModule;
import robotx.stx_libraries.components.XMotor;

public class Spindexer extends XModule {

    XMotor motor;

    public Spindexer(OpMode op) {
        super(op);
    }

    @Override
    public void init() {
        motor = new XMotor(opMode, "spindexer");
        motor.init();
        loopMotors.add(motor);
        super.init();
    }

    @Override
    public void control_loop() {
        super.control_loop();
        if(xGamepad1.left_bumper.isDown()){
            motor.setPower(-0.5);
        } else if(xGamepad1.right_bumper.isDown()){
            motor.setPower(0.5);
        } else {
            motor.setPower(0);
        }
    }
}
