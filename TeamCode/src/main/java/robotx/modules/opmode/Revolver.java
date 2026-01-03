package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import robotx.stx_libraries.XModule;
import robotx.stx_libraries.components.XMotor;

public class Revolver extends XModule {

    XMotor motor;

    static final int sixth = 0;

    boolean isIntaking = false;

    public Revolver(OpMode op) {
        super(op);

    }

    @Override
    public void init() {
        motor = new XMotor(opMode, "revolverMotor", 0L);
        loopMotors.add(motor);
        super.init();
    }

    @Override
    public void control_loop() {
        super.control_loop();

        if (!dualPlayer) {
            if (xGamepad1.right_bumper.wasPressed()) {
                motor.increment(sixth * 2);
            }
            if (xGamepad1.left_bumper.wasPressed()) {
                motor.increment(sixth * -2);
            }
            if (xGamepad1.x.wasPressed()) {
                isIntaking = !isIntaking;
                increment();
            }
            if (isIntaking) {
                if (xGamepad1.b.wasPressed()) {
                    isIntaking = false;
                    increment();
                }
            } else {
                if (xGamepad1.a.wasPressed()) {
                    isIntaking = true;
                    increment();
                }
            }
        } else {
            if (xGamepad2.right_bumper.wasPressed()) {
                motor.increment(sixth * 2);
            }
            if (xGamepad2.left_bumper.wasPressed()) {
                motor.increment(sixth * -2);
            }
            if (xGamepad2.x.wasPressed()) {
                isIntaking = !isIntaking;
                increment();
            }
            if (isIntaking) {
                if (xGamepad2.b.wasPressed()) {
                    isIntaking = false;
                    increment();
                }
            } else {
                if (xGamepad2.a.wasPressed()) {
                    isIntaking = true;
                    increment();
                }
            }
        }
    }

    public void increment() {
        if (isIntaking) {
            motor.increment(sixth);
        } else {
            motor.increment(-sixth);
        }
    }
}
