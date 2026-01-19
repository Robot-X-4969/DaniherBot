package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import robotx.stx_libraries.XModule;
import robotx.stx_libraries.components.XMotor;
import robotx.stx_libraries.components.XServo;

public class FlyWheel extends XModule {

    private static final double[] MOTOR_SPEEDS = new double[]{
            0.0, 0.5, 0.5
    };

    private static final double[] SERVO_POSITIONS = new double[]{
            0.0, 0.125, 0.16
    };

    XMotor motor1, motor2;
    XServo servo1, servo2;

    private int index = 0;

    public FlyWheel(OpMode op) {
        super(op);
    }

    @Override
    public void init() {
        motor1 = new XMotor(opMode, "flywheel1", 0);
        motor1.init();
        motor1.setBrakes(false);

        motor2 = new XMotor(opMode, "flywheel2", 0);
        motor2.init();
        motor2.setBrakes(false);

        servo1 = new XServo(opMode, "hood1", SERVO_POSITIONS[0]);
        servo1.init();

        servo2 = new XServo(opMode, "hood2", 0.99 - SERVO_POSITIONS[0]);
        servo2.init();

        increment(0);

        super.init();
    }

    @Override
    public void control_loop() {
        super.control_loop();

        if(dualPlayer){
            if (xGamepad1.dpad_left.wasPressed() || xGamepad2.dpad_left.wasPressed()) {
                increment(-1);
            } else if (xGamepad1.dpad_right.wasPressed() || xGamepad2.dpad_right.wasPressed()) {
                increment(1);
            }
        } else {
            if (xGamepad1.dpad_left.wasPressed()) {
                increment(-1);
            } else if (xGamepad1.dpad_right.wasPressed()) {
                increment(1);
            }
        }
    }

    public void increment(int increment) {
        index = Math.max(0, Math.min(index + increment, MOTOR_SPEEDS.length - 1));

        motor1.setPower(MOTOR_SPEEDS[index]);
        motor2.setPower(MOTOR_SPEEDS[index]);

        servo1.setPosition(SERVO_POSITIONS[index]);
        servo2.setPosition(0.99 - SERVO_POSITIONS[index]);
    }

}
