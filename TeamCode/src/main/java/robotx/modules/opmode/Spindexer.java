package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import robotx.stx_libraries.XModule;
import robotx.stx_libraries.components.XMotor;
import robotx.stx_libraries.components.XServo;

public class Spindexer extends XModule {

    private static final int INCREMENT = 475;
    private static final double START_ANGLE = 40.0 / 360.0 / 5.0;

    XMotor motor;

    XServo gate1, gate2;

    public Spindexer(OpMode op) {
        super(op);
    }

    @Override
    public void init() {
        motor = new XMotor(opMode, "spindexer");
        motor.init();
        loopMotors.add(motor);

        gate1 = new XServo(opMode, "gate1", 1-START_ANGLE);
        gate1.init();
        gate2 = new XServo(opMode, "gate2", START_ANGLE);
        gate2.init();

        super.init();
    }

    @Override
    public void loop(){
        super.loop();
        motor.hold(1, 0.75);
    }

    @Override
    public void control_loop() {
        super.control_loop();

        if(dualPlayer){
            if (xGamepad1.left_bumper.wasPressed() || xGamepad2.left_bumper.wasPressed()) {
                motor.increment(-INCREMENT, -0.75);
            } else if (xGamepad1.right_bumper.wasPressed() || xGamepad2.right_bumper.wasPressed()) {
                motor.increment(INCREMENT, -0.75);
            }

            if (xGamepad1.b.wasPressed() || xGamepad2.b.wasPressed()) {
                gate1.increment(-2.0 / 15.0);
                gate2.increment(2.0 / 15.0);
                scheduler.setEvent(1000L, "resetGate", () -> {
                    gate1.setPosition(1-START_ANGLE);
                    gate2.setPosition(START_ANGLE);
                });
            }

            if(xGamepad1.x.isDown() || xGamepad2.x.isDown()){
                motor.setIndefiniteRotation(0.1);
            } else if(xGamepad1.x.wasReleased() || xGamepad2.x.wasReleased()){
                motor.reset();
            }
        } else {
            if (xGamepad1.left_bumper.wasPressed()) {
                motor.increment(-INCREMENT, -0.75);
            } else if (xGamepad1.right_bumper.wasPressed()) {
                motor.increment(INCREMENT, -0.75);
            }

            if (xGamepad1.right_trigger >= 0.25) {
                gate1.increment(-2.0 / 15.0);
                gate2.increment(2.0 / 15.0);
                scheduler.setEvent(1000L, "resetGate", () -> {
                    gate1.setPosition(1-START_ANGLE);
                    gate2.setPosition(START_ANGLE);
                });
            }

            if(xGamepad1.x.isDown()){
                motor.setIndefiniteRotation(0.1);
            } else if(xGamepad1.x.wasReleased()){
                motor.reset();
            }
        }
    }
}
