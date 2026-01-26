package robotx.modules.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import robotx.stx_libraries.XModule;
import robotx.stx_libraries.components.XMotor;
import robotx.stx_libraries.components.XServo;

public class FlyWheel extends XModule {

    private static final double[] MOTOR_SPEEDS = new double[]{
            0.0, 750.0, 825.0, 1025.0
    };

    private static final double[] SERVO_POSITIONS = new double[]{
            0.0, 0.20, 0.125, 0.16
    };

    XMotor motor1, motor2;
    XServo servo1, servo2;

    double lastTime = 0.0;

    private int index = 0;

    public FlyWheel(OpMode op) {
        super(op);
    }

    @Override
    public void init() {
        motor1 = new XMotor(opMode, "flywheel1", 103.8);
        motor2 = new XMotor(opMode, "flywheel2", 103.8);

        loopMotors.add(motor1);
        loopMotors.add(motor2);

        super.init();

        motor1.toggleSafe();
        motor1.setBrakes(false);
        motor1.setMotorMode(true);
        motor1.setMaxVelocity(motor1.calculateVelocity(1400));

        motor2.toggleSafe();
        motor2.setBrakes(false);
        motor2.setMotorMode(true);
        motor2.setMaxVelocity(motor2.calculateVelocity(1400));

        setPIDFCoefficients();

        servo1 = new XServo(opMode, "hood1", SERVO_POSITIONS[0]);
        servo1.init();

        servo2 = new XServo(opMode, "hood2", 0.99 - SERVO_POSITIONS[0]);
        servo2.init();

        lastTime = System.currentTimeMillis() / 1000.0;

        increment(0);
    }

    @Override
    public void loop(){
        super.loop();
        opMode.telemetry.addData("CurrentRPM",motor1.getCurrentRPM(motor1.getCurrentVelocity()));
        opMode.telemetry.addData("TargetRPM", MOTOR_SPEEDS[index]);
        opMode.telemetry.update();
    }

    @Override
    public void control_loop() {
        super.control_loop();

        if (xDS.xGamepad1.dpad_left.wasPressed()) {
            increment(-1);
        } else if (xDS.xGamepad1.dpad_right.wasPressed()) {
            increment(1);
        }
    }

    public void increment(int increment) {
        index = Math.max(0, Math.min(index + increment, MOTOR_SPEEDS.length - 1));

        motor1.setMotorRPM(MOTOR_SPEEDS[index]);
        motor2.setMotorRPM(MOTOR_SPEEDS[index]);

        servo1.setPosition(SERVO_POSITIONS[index]);
        servo2.setPosition(0.99 - SERVO_POSITIONS[index]);
    }

    public void setPIDFCoefficients(){
        double F = 32767 / motor1.getMaxVelocity();
        double P = 0.1 * F;
        double I = 0.0005 * P;
        double D = 0.0;

        motor1.setPIDFValues(P, I, D, F);
        motor2.setPIDFValues(P, I, D, F);
    }
}
