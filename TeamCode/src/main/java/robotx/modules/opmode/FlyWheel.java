package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import robotx.stx_libraries.XModule;
import robotx.stx_libraries.components.XMotor;

public class FlyWheel extends XModule {

    XMotor motor1;
    XMotor motor2;

    double[] motor_speeds = {0.1, 0.2, 0.3, 0.4, 0.5};

    int i = 0;

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

    }

    public void control_loop(){
        super.control_loop();

        if(!dualPlayer){
            if(xGamepad1.dpad_left.isDown()){
                if((i + 1) != motor_speeds.length) {
                    motor1.setPower(motor_speeds[i + 1]);
                }
            }
            else if(xGamepad1.dpad_right.isDown()){
                if((i - 1) != -1) {
                    motor1.setPower(motor_speeds[i - 1]);
                }
            }
        }
        else {
            if(xGamepad2.dpad_left.isDown()){
                if((i + 1) != motor_speeds.length) {
                    motor1.setPower(motor_speeds[i + 1]);
                }
            }
            else if(xGamepad2.dpad_right.isDown()){
                if((i - 1) != -1) {
                    motor1.setPower(motor_speeds[i - 1]);
                }
            }

        }



    }

    @Override
    public void control_loop() {
        super.control_loop();
        if (dualPlayer) {
            if (xGamepad1.a.wasPressed()) {
                run = !run;
            }
        } else {
            if (xGamepad1.a.wasPressed()) {
                run = !run;
            }
        }

    }
}
