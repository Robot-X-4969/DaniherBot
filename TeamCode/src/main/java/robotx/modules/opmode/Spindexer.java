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

        if (xDS.xGamepad.left_bumper.wasPressed()) {
            motor.increment(-INCREMENT, -0.75);
        } else if (xDS.xGamepad.right_bumper.wasPressed()) {
            motor.increment(INCREMENT, -0.75);
        }

        if (xDS.xGamepad.b.wasPressed()) {
            gate1.increment(-2.0 / 15.0);
            gate2.increment(2.0 / 15.0);
            scheduler.setEvent(1000L, "resetGate", () -> {
                gate1.setPosition(1-START_ANGLE);
                gate2.setPosition(START_ANGLE);
            });
        }

        if(xDS.xGamepad.x.isDown()){
            motor.setIndefiniteRotation(0.1);
        } else if(xDS.xGamepad.x.wasReleased()){
            motor.reset();
        }
    }
}
