package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import robotx.stx_libraries.XModule;
import robotx.stx_libraries.components.XMotor;

public class FlyWheel extends XModule {

    XMotor motor1;
    XMotor motor2;

    boolean run = false;

    public FlyWheel(OpMode op) {
        super(op);
    }

    @Override
    public void init() {
        motor1 = new XMotor(opMode, "flyWheel1", 0);
        motor1.init();
        motor1.setBrakes(false);

        motor2 = new XMotor(opMode, "flyWheel2", 0);
        motor2.init();
        motor2.setBrakes(false);

        super.init();
    }

    @Override
    public void loop() {
        super.loop();
        if(run){
            motor1.setPower(-1);
            motor2.setPower(1);
        }
        else {
            motor1.setPower(0);
            motor2.setPower(0);
        }

    }

    @Override
    public void control_loop(){
        super.control_loop();
        if(xGamepad1.a.wasPressed()){
            run = !run;
        }
    }

}
