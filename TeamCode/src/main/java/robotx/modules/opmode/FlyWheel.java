package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import robotx.stx_libraries.XModule;
import robotx.stx_libraries.components.XGamepad;
import robotx.stx_libraries.components.XMotor;

public class FlyWheel extends XModule {

    XMotor motor1;
    XMotor motor2;

    Servo servo;

    boolean isOpen = false;

    int index = 0;
    static final double[] speeds = {0.0, 1.0, 2.0, 3.0};

    public FlyWheel(OpMode op) {
        super(op);
    }

    @Override
    public void init() {
        motor1 = new XMotor(opMode, "flyWheel1", 0);
        motor2 = new XMotor(opMode, "flyWheel2", 0);
        servo = opMode.hardwareMap.get(Servo.class, "gate");

        loopMotors.add(motor1);
        loopMotors.add(motor2);

        motor1.setPower(0.5);
        motor2.setPower(-0.5);

        servo.setPosition(0.0);

        super.init();
    }

    @Override
    public void loop(){
        super.loop();

        motor1.setRPM(speeds[index]);
        motor2.setRPM(-speeds[index]);

    }

    @Override
    public void control_loop(){
        super.control_loop();
        if(xGamepad1.dpad_left.wasPressed()){
            index = Math.max(index-1, 0);
        }
        else if(xGamepad1.dpad_right.wasPressed()){
            index = Math.max(index+1, 3);
        }

        if(xGamepad1.b.wasPressed()){
            isOpen = !isOpen;
            setServo();
        }
    }
    
    public void setServo(){
        if(isOpen){
            servo.setPosition(1.0);
        }
        else{
            servo.setPosition(0.0);
        }
    }


}
